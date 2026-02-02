package com.example.masterrollerdice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.masterrollerdice.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiceViewModel by activityViewModels()
    private val SviewModel: SettingsViewModel by activityViewModels()

    // Variable para almacenar el estado actual del modo nocturno
    private var isNightMode = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        // Observar cambios en el tema
        SviewModel.nightMode.observe(viewLifecycleOwner) { nightMode ->
            isNightMode = nightMode
            applyThemeColors(nightMode)
            updateStatistics(viewModel.rollHistory.value ?: emptyList())
        }
    }

    private fun applyThemeColors(isNightMode: Boolean) {
        // Colores del fondo principal
        val backColor = if (isNightMode) {
            ContextCompat.getColor(requireContext(), R.color.dark_blue)
        } else {
            ContextCompat.getColor(requireContext(), R.color.lavender)
        }

        // Colores del texto del título
        val titleColor = if (isNightMode) {
            ContextCompat.getColor(requireContext(), R.color.white)
        } else {
            ContextCompat.getColor(requireContext(), R.color.dark_blue)
        }

        // Aplicar colores
        binding.layoutStatistics.setBackgroundColor(backColor)
        binding.titleText.setTextColor(titleColor)
    }

    private fun setupObservers() {
        viewModel.rollHistory.observe(viewLifecycleOwner) { history ->
            updateStatistics(history)
        }
    }

    private fun updateStatistics(history: List<DiceRoll>) {
        binding.statsContainer.removeAllViews()

        if (history.isEmpty()) {
            val emptyText = TextView(requireContext()).apply {
                text = "No hay estadísticas disponibles\nRealiza algunas tiradas primero"
                textSize = 16f
                setTextColor(
                    if (isNightMode)
                        ContextCompat.getColor(requireContext(), R.color.white)
                    else
                        ContextCompat.getColor(requireContext(), R.color.dark_blue)
                )
                gravity = android.view.Gravity.CENTER
                setPadding(16, 32, 16, 32)
            }
            binding.statsContainer.addView(emptyText)
            return
        }

        // Calcular estadísticas
        val stats = calculateStatistics(history)

        // Crear tarjeta de resumen general
        binding.statsContainer.addView(createSummaryCard(stats))

        // Crear tarjetas por tipo de dado
        if (stats.d4Stats.timesRolled > 0) {
            binding.statsContainer.addView(createDiceStatsCard("D4", stats.d4Stats))
        }
        if (stats.d6Stats.timesRolled > 0) {
            binding.statsContainer.addView(createDiceStatsCard("D6", stats.d6Stats))
        }
        if (stats.d8Stats.timesRolled > 0) {
            binding.statsContainer.addView(createDiceStatsCard("D8", stats.d8Stats))
        }
        if (stats.d10Stats.timesRolled > 0) {
            binding.statsContainer.addView(createDiceStatsCard("D10", stats.d10Stats))
        }
        if (stats.d12Stats.timesRolled > 0) {
            binding.statsContainer.addView(createDiceStatsCard("D12", stats.d12Stats))
        }
        if (stats.d20Stats.timesRolled > 0) {
            binding.statsContainer.addView(createDiceStatsCard("D20", stats.d20Stats))
        }
        if (stats.d100Stats.timesRolled > 0) {
            binding.statsContainer.addView(createDiceStatsCard("D100", stats.d100Stats))
        }
    }

    private fun calculateStatistics(history: List<DiceRoll>): GameStatistics {
        val totalRolls = history.size
        var totalDiceRolled = 0
        var totalSum = 0

        val d4Stats = DiceStatistics()
        val d6Stats = DiceStatistics()
        val d8Stats = DiceStatistics()
        val d10Stats = DiceStatistics()
        val d12Stats = DiceStatistics()
        val d20Stats = DiceStatistics()
        val d100Stats = DiceStatistics()

        history.forEach { roll ->
            totalDiceRolled += roll.d4Count + roll.d6Count + roll.d8Count +
                    roll.d10Count + roll.d12Count + roll.d20Count + roll.d100Count
            totalSum += roll.total

            // Acumular estadísticas por tipo de dado
            d4Stats.timesRolled += roll.d4Count
            d6Stats.timesRolled += roll.d6Count
            d8Stats.timesRolled += roll.d8Count
            d10Stats.timesRolled += roll.d10Count
            d12Stats.timesRolled += roll.d12Count
            d20Stats.timesRolled += roll.d20Count
            d100Stats.timesRolled += roll.d100Count

            // Calcular sumas (aproximación basada en el total de la tirada)
            val totalDiceInRoll = roll.d4Count + roll.d6Count + roll.d8Count +
                    roll.d10Count + roll.d12Count + roll.d20Count + roll.d100Count

            if (totalDiceInRoll > 0) {
                val avgPerDice = roll.total.toDouble() / totalDiceInRoll

                d4Stats.totalSum += (avgPerDice * roll.d4Count).toInt()
                d6Stats.totalSum += (avgPerDice * roll.d6Count).toInt()
                d8Stats.totalSum += (avgPerDice * roll.d8Count).toInt()
                d10Stats.totalSum += (avgPerDice * roll.d10Count).toInt()
                d12Stats.totalSum += (avgPerDice * roll.d12Count).toInt()
                d20Stats.totalSum += (avgPerDice * roll.d20Count).toInt()
                d100Stats.totalSum += (avgPerDice * roll.d100Count).toInt()
            }
        }

        return GameStatistics(
            totalRolls = totalRolls,
            totalDiceRolled = totalDiceRolled,
            totalSum = totalSum,
            d4Stats = d4Stats,
            d6Stats = d6Stats,
            d8Stats = d8Stats,
            d10Stats = d10Stats,
            d12Stats = d12Stats,
            d20Stats = d20Stats,
            d100Stats = d100Stats
        )
    }

    private fun createSummaryCard(stats: GameStatistics): View {
        val cardView = CardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 24
            }
            radius = 24f
            setCardBackgroundColor(
                if (isNightMode)
                    ContextCompat.getColor(requireContext(), R.color.gray)
                else
                    ContextCompat.getColor(requireContext(), R.color.dark_blue)
            )
            cardElevation = 8f
        }

        val contentLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleText = TextView(requireContext()).apply {
            text = " Resumen General"
            textSize = 20f
            setTextColor(
                //if (isNightMode)
                //    ContextCompat.getColor(requireContext(), R.color.dark_blue)
                //else
                    ContextCompat.getColor(requireContext(), R.color.white)
            )
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val totalRollsText = TextView(requireContext()).apply {
            text = "Total de tiradas: ${stats.totalRolls}"
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
            setPadding(0, 16, 0, 0)
        }

        val totalDiceText = TextView(requireContext()).apply {
            text = "Dados lanzados: ${stats.totalDiceRolled}"
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
            setPadding(0, 8, 0, 0)
        }

        val totalSumText = TextView(requireContext()).apply {
            text = "Suma total: ${stats.totalSum}"
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
            setPadding(0, 8, 0, 0)
        }

        val avgText = TextView(requireContext()).apply {
            val avg = if (stats.totalRolls > 0)
                String.format("%.2f", stats.totalSum.toDouble() / stats.totalRolls)
            else "0.00"
            text = "Promedio por tirada: $avg"
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
            setPadding(0, 8, 0, 0)
        }

        contentLayout.addView(titleText)
        contentLayout.addView(totalRollsText)
        contentLayout.addView(totalDiceText)
        contentLayout.addView(totalSumText)
        contentLayout.addView(avgText)
        cardView.addView(contentLayout)

        return cardView
    }

    private fun createDiceStatsCard(diceName: String, stats: DiceStatistics): View {
        val cardView = CardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 24
            }
            radius = 24f
            setCardBackgroundColor(
                if (isNightMode)
                    ContextCompat.getColor(requireContext(), R.color.gray)
                else
                    ContextCompat.getColor(requireContext(), R.color.dark_blue)
            )
            cardElevation = 8f
        }

        val contentLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val titleText = TextView(requireContext()).apply {
            text = "$diceName"
            textSize = 18f
            setTextColor(
                //if (isNightMode)
                  //  ContextCompat.getColor(requireContext(), R.color.dark_blue)
                //else
                    ContextCompat.getColor(requireContext(), R.color.white)
            )
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val timesRolledText = TextView(requireContext()).apply {
            text = "Veces lanzado: ${stats.timesRolled}"
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
            setPadding(0, 12, 0, 0)
        }

        val totalSumText = TextView(requireContext()).apply {
            text = "Suma total: ${stats.totalSum}"
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
            setPadding(0, 8, 0, 0)
        }

        val avgText = TextView(requireContext()).apply {
            val avg = if (stats.timesRolled > 0)
                String.format("%.2f", stats.totalSum.toDouble() / stats.timesRolled)
            else "0.00"
            text = "Promedio: $avg"
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
            setPadding(0, 8, 0, 0)
        }

        contentLayout.addView(titleText)
        contentLayout.addView(timesRolledText)
        contentLayout.addView(totalSumText)
        contentLayout.addView(avgText)
        cardView.addView(contentLayout)

        return cardView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Clases de datos para las estadísticas
    data class DiceStatistics(
        var timesRolled: Int = 0,
        var totalSum: Int = 0
    )

    data class GameStatistics(
        val totalRolls: Int,
        val totalDiceRolled: Int,
        val totalSum: Int,
        val d4Stats: DiceStatistics,
        val d6Stats: DiceStatistics,
        val d8Stats: DiceStatistics,
        val d10Stats: DiceStatistics,
        val d12Stats: DiceStatistics,
        val d20Stats: DiceStatistics,
        val d100Stats: DiceStatistics
    )
}