package com.example.masterrollerdice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.masterrollerdice.databinding.FragmentStatistcstwoBinding


class StatisticstwoFragment : Fragment() {

    private var _binding: FragmentStatistcstwoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiceViewModel by activityViewModels()
    private val SviewModel: SettingsViewModel by activityViewModels()

    // Variable para almacenar el estado actual del modo nocturno
    private var isNightMode = false
    // Variable para almacenar las estadísticas calculadas
    private lateinit var allStats: GameStatistics
    // Variable para almacenar el tipo de dado seleccionado ("ALL", "D4", etc.)
    private var selectedDiceType: String = "ALL"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatistcstwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupDiceFilterSpinner()

        // Observar cambios en el tema
        SviewModel.nightMode.observe(viewLifecycleOwner) { nightMode ->
            isNightMode = nightMode
            applyThemeColors(nightMode)
            // Actualizar las tarjetas existentes con los nuevos colores
            updateDisplayForCurrentSelection()
        }
    }

    private fun setupDiceFilterSpinner() {
        val spinner = binding.diceFilterSpinner
        val diceOptions = listOf("Resumen General", "D4", "D6", "D8", "D10", "D12", "D20", "D100")

        // Crea el adaptador personalizado para controlar los colores del texto
        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, // Layout para la vista principal
            diceOptions
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (view is TextView) {
                    // Aplica color blanco al texto del spinner principal
                    view.setTextColor(ContextCompat.getColor(context, R.color.white)) // Cambia a tu recurso de color blanco si es diferente
                    // Opcional: Aplica un fondo gris al spinner principal
                    // view.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                }
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                if (view is TextView) {
                    // Aplica color blanco al texto del dropdown
                    view.setTextColor(ContextCompat.getColor(context, R.color.dark_blue)) // Cambia a tu recurso de color blanco si es diferente
                    // Opcional: Aplica un fondo gris a las opciones del dropdown
                    // view.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
                }
                return view
            }
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Layout para las vistas del dropdown
        spinner.adapter = adapter

        // Opcional: Aplica el fondo gris directamente al spinner si no se hace en XML
         //spinner.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> selectedDiceType = "ALL"
                    1 -> selectedDiceType = "D4"
                    2 -> selectedDiceType = "D6"
                    3 -> selectedDiceType = "D8"
                    4 -> selectedDiceType = "D10"
                    5 -> selectedDiceType = "D12"
                    6 -> selectedDiceType = "D20"
                    7 -> selectedDiceType = "D100"
                }
                updateDisplayForCurrentSelection()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
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

        // Aplicar color al spinner si es necesario (esto podría sobreescribir la configuración del adaptador si se aplica aquí)
        // Por eso es mejor hacerlo en el adaptador o en XML
        // val spinnerBackColor = if (isNightMode) {
        //     ContextCompat.getColor(requireContext(), R.color.gray)
        // } else {
        //     ContextCompat.getColor(requireContext(), R.color.gray) // Misma elección para ambos modos
        // }
        // binding.dice_filter_spinner.setBackgroundColor(spinnerBackColor)
    }

    private fun setupObservers() {
        viewModel.rollHistory.observe(viewLifecycleOwner) { history ->
            allStats = calculateStatistics(history)
            updateDisplayForCurrentSelection()
        }
    }

    private fun updateDisplayForCurrentSelection() {
        binding.statsContainer.removeAllViews()

        if (!::allStats.isInitialized || allStats.totalRolls == 0) {
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

        when (selectedDiceType) {
            "ALL" -> {
                binding.statsContainer.addView(createSummaryCard(allStats))
            }
            "D4" -> {
                if (allStats.d4Stats.timesRolled > 0) {
                    binding.statsContainer.addView(createDetailedDiceStatsCard("D4", allStats.d4Stats))
                } else {
                    showNoDataForDice("D4")
                }
            }
            "D6" -> {
                if (allStats.d6Stats.timesRolled > 0) {
                    binding.statsContainer.addView(createDetailedDiceStatsCard("D6", allStats.d6Stats))
                } else {
                    showNoDataForDice("D6")
                }
            }
            "D8" -> {
                if (allStats.d8Stats.timesRolled > 0) {
                    binding.statsContainer.addView(createDetailedDiceStatsCard("D8", allStats.d8Stats))
                } else {
                    showNoDataForDice("D8")
                }
            }
            "D10" -> {
                if (allStats.d10Stats.timesRolled > 0) {
                    binding.statsContainer.addView(createDetailedDiceStatsCard("D10", allStats.d10Stats))
                } else {
                    showNoDataForDice("D10")
                }
            }
            "D12" -> {
                if (allStats.d12Stats.timesRolled > 0) {
                    binding.statsContainer.addView(createDetailedDiceStatsCard("D12", allStats.d12Stats))
                } else {
                    showNoDataForDice("D12")
                }
            }
            "D20" -> {
                if (allStats.d20Stats.timesRolled > 0) {
                    binding.statsContainer.addView(createDetailedDiceStatsCard("D20", allStats.d20Stats))
                } else {
                    showNoDataForDice("D20")
                }
            }
            "D100" -> {
                if (allStats.d100Stats.timesRolled > 0) {
                    binding.statsContainer.addView(createDetailedDiceStatsCard("D100", allStats.d100Stats))
                } else {
                    showNoDataForDice("D100")
                }
            }
        }
    }

    private fun showNoDataForDice(diceName: String) {
        val noDataText = TextView(requireContext()).apply {
            text = "No se han realizado tiradas de $diceName aún."
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
        binding.statsContainer.addView(noDataText)
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

            // Procesar resultados de D4
            var d4Index = 0
            repeat(roll.d4Count) {
                if (d4Index < roll.results.size) {
                    val value = roll.results[d4Index]
                    d4Stats.timesRolled++
                    d4Stats.totalSum += value
                    d4Stats.valuesCount[value] = (d4Stats.valuesCount[value] ?: 0) + 1
                    d4Index++
                }
            }

            // Procesar resultados de D6
            var d6Index = d4Index
            repeat(roll.d6Count) {
                if (d6Index < roll.results.size) {
                    val value = roll.results[d6Index]
                    d6Stats.timesRolled++
                    d6Stats.totalSum += value
                    d6Stats.valuesCount[value] = (d6Stats.valuesCount[value] ?: 0) + 1
                    d6Index++
                }
            }

            // Procesar resultados de D8
            var d8Index = d6Index
            repeat(roll.d8Count) {
                if (d8Index < roll.results.size) {
                    val value = roll.results[d8Index]
                    d8Stats.timesRolled++
                    d8Stats.totalSum += value
                    d8Stats.valuesCount[value] = (d8Stats.valuesCount[value] ?: 0) + 1
                    d8Index++
                }
            }

            // Procesar resultados de D10
            var d10Index = d8Index
            repeat(roll.d10Count) {
                if (d10Index < roll.results.size) {
                    val value = roll.results[d10Index]
                    d10Stats.timesRolled++
                    d10Stats.totalSum += value
                    d10Stats.valuesCount[value] = (d10Stats.valuesCount[value] ?: 0) + 1
                    d10Index++
                }
            }

            // Procesar resultados de D12
            var d12Index = d10Index
            repeat(roll.d12Count) {
                if (d12Index < roll.results.size) {
                    val value = roll.results[d12Index]
                    d12Stats.timesRolled++
                    d12Stats.totalSum += value
                    d12Stats.valuesCount[value] = (d12Stats.valuesCount[value] ?: 0) + 1
                    d12Index++
                }
            }

            // Procesar resultados de D20
            var d20Index = d12Index
            repeat(roll.d20Count) {
                if (d20Index < roll.results.size) {
                    val value = roll.results[d20Index]
                    d20Stats.timesRolled++
                    d20Stats.totalSum += value
                    d20Stats.valuesCount[value] = (d20Stats.valuesCount[value] ?: 0) + 1
                    d20Index++
                }
            }

            // Procesar resultados de D100
            var d100Index = d20Index
            repeat(roll.d100Count) {
                if (d100Index < roll.results.size) {
                    val value = roll.results[d100Index]
                    d100Stats.timesRolled++
                    d100Stats.totalSum += value
                    d100Stats.valuesCount[value] = (d100Stats.valuesCount[value] ?: 0) + 1
                    d100Index++
                }
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

    private fun createDetailedDiceStatsCard(diceName: String, stats: DiceStatistics): View {
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
            text = "Estadísticas de $diceName"
            textSize = 18f
            setTextColor(
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

        // Mostrar valores que han salido
        if (stats.valuesCount.isNotEmpty()) {
            val valuesTitle = TextView(requireContext()).apply {
                text = "\nValores obtenidos:"
                textSize = 16f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
                setPadding(0, 12, 0, 8)
            }
            contentLayout.addView(valuesTitle)

            val sortedValues = stats.valuesCount.keys.sorted()
            val valuesText = StringBuilder()
            for (value in sortedValues) {
                if (valuesText.isNotEmpty()) valuesText.append(", ")
                valuesText.append("$value (${stats.valuesCount[value]} veces)")
            }

            val valuesDetail = TextView(requireContext()).apply {
                text = valuesText.toString()
                textSize = 14f
                setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
                setPadding(0, 0, 0, 8)
            }
            contentLayout.addView(valuesDetail)
        }

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
        var totalSum: Int = 0,
        var valuesCount: MutableMap<Int, Int> = mutableMapOf()
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