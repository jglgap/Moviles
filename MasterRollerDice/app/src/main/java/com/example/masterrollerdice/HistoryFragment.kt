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
import com.example.masterrollerdice.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
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
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        setupObservers() // ✅ Llamar después de setupClickListeners

        // ✅ UN SOLO observador para nightMode
        SviewModel.nightMode.observe(viewLifecycleOwner) { nightMode ->
            isNightMode = nightMode  // ✅ Actualizar la variable
            applyThemeColors(nightMode) // ✅ Aplicar colores del tema
            updateHistoryView(viewModel.rollHistory.value ?: emptyList()) // ✅ Refrescar el historial
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

        // Colores del botón
        val btnBackColor = if (isNightMode) {
            ContextCompat.getColor(requireContext(), R.color.lavender)
        } else {
            ContextCompat.getColor(requireContext(), R.color.dark_blue)
        }

        val btnTextColor = if (isNightMode) {
            ContextCompat.getColor(requireContext(), R.color.dark_blue)
        } else {
            ContextCompat.getColor(requireContext(), R.color.white)
        }

        // Aplicar colores
        binding.layoutHistory.setBackgroundColor(backColor)
        binding.titleText.setTextColor(titleColor)
        binding.btnClearHistory.setTextColor(btnTextColor)
        binding.btnClearHistory.setBackgroundColor(btnBackColor)
    }

    private fun setupObservers() {
        viewModel.rollHistory.observe(viewLifecycleOwner) { history ->
            updateHistoryView(history)
        }
    }

    private fun setupClickListeners() {
        binding.btnClearHistory.setOnClickListener {
            viewModel.clearHistory()
        }
    }

    private fun updateHistoryView(history: List<DiceRoll>) {
        binding.historyContainer.removeAllViews()

        if (history.isEmpty()) {
            val emptyText = TextView(requireContext()).apply {
                text = "No hay tiradas en el historial"
                textSize = 16f
                // ✅ Usar isNightMode actualizado
                setTextColor(
                    if (isNightMode)
                        ContextCompat.getColor(requireContext(), R.color.white)
                    else
                        ContextCompat.getColor(requireContext(), R.color.dark_blue)
                )
                gravity = android.view.Gravity.CENTER
                setPadding(16, 32, 16, 32)
            }
            binding.historyContainer.addView(emptyText)
            return
        }

        history.forEach { diceRoll ->
            val historyItem = createHistoryItem(diceRoll)
            binding.historyContainer.addView(historyItem)
        }
    }

    private fun createHistoryItem(diceRoll: DiceRoll): View {
        val cardView = CardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 24
            }
            radius = 24f
            // ✅ Usar isNightMode actualizado
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

        val dateText = TextView(requireContext()).apply {
            text = diceRoll.date
            textSize = 14f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.mustard))
        }

        val diceInfoText = TextView(requireContext()).apply {
            text = diceRoll.getDiceDescription()
            textSize = 16f
            // ✅ Usar isNightMode actualizado
            setTextColor(
               // if (isNightMode)
               //     ContextCompat.getColor(requireContext(), R.color.dark_blue)
                //else
                    ContextCompat.getColor(requireContext(), android.R.color.white)
            )
            setPadding(0, 8, 0, 0)
        }

        val formulaText = TextView(requireContext()).apply {
            text = diceRoll.getFormula()
            textSize = 14f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
            setPadding(0, 8, 0, 0)
        }

        contentLayout.addView(dateText)
        contentLayout.addView(diceInfoText)
        contentLayout.addView(formulaText)
        cardView.addView(contentLayout)

        return cardView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}