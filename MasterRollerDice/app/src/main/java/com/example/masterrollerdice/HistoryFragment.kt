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
import com.example.masterrollerdice.R
import com.example.masterrollerdice.databinding.FragmentHistoryBinding
import com.example.masterrollerdice.DiceRoll
import com.example.masterrollerdice.DiceViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiceViewModel by activityViewModels()

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

        setupObservers()
        setupClickListeners()
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
                setTextColor(ContextCompat.getColor(requireContext(), android.R.color.darker_gray))
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
            setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.card_background))
            cardElevation = 8f
        }

        val contentLayout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val dateText = TextView(requireContext()).apply {
            text = diceRoll.date
            textSize = 14f
            setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary))
        }

        val diceInfoText = TextView(requireContext()).apply {
            text = diceRoll.getDiceDescription()
            textSize = 16f
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
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
