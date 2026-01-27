package com.example.masterrollerdice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.masterrollerdice.databinding.FragmentDiceBinding
import com.example.masterrollerdice.DiceViewModel

class DiceFragment : Fragment() {

    private var _binding: FragmentDiceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DiceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.d4Count.observe(viewLifecycleOwner) { count ->
            binding.countD4.text = "x$count"
        }

        viewModel.d6Count.observe(viewLifecycleOwner) { count ->
            binding.countD6.text = "x$count"
        }

        viewModel.d8Count.observe(viewLifecycleOwner) { count ->
            binding.countD8.text = "x$count"
        }

        viewModel.d10Count.observe(viewLifecycleOwner) { count ->
            binding.countD10.text = "x$count"
        }

        viewModel.d12Count.observe(viewLifecycleOwner) { count ->
            binding.countD12.text = "x$count"
        }

        viewModel.d20Count.observe(viewLifecycleOwner) { count ->
            binding.countD20.text = "x$count"
        }

        viewModel.d100Count.observe(viewLifecycleOwner) { count ->
            binding.countD100.text = "x$count"
        }

        viewModel.rollResult.observe(viewLifecycleOwner) { (total, breakdown) ->
            binding.totalSum.text = total.toString()
            binding.diceBreakdown.text = breakdown
        }
    }

    private fun setupClickListeners() {
        binding.btnAddD4.setOnClickListener { viewModel.incrementD4() }
        binding.btnRemoveD4.setOnClickListener { viewModel.decrementD4() }

        binding.btnAddD6.setOnClickListener { viewModel.incrementD6() }
        binding.btnRemoveD6.setOnClickListener { viewModel.decrementD6() }

        binding.btnAddD8.setOnClickListener { viewModel.incrementD8() }
        binding.btnRemoveD8.setOnClickListener { viewModel.decrementD8() }

        binding.btnAddD10.setOnClickListener { viewModel.incrementD10() }
        binding.btnRemoveD10.setOnClickListener { viewModel.decrementD10() }

        binding.btnAddD12.setOnClickListener { viewModel.incrementD12() }
        binding.btnRemoveD12.setOnClickListener { viewModel.decrementD12() }

        binding.btnAddD20.setOnClickListener { viewModel.incrementD20() }
        binding.btnRemoveD20.setOnClickListener { viewModel.decrementD20() }

        binding.btnAddD100.setOnClickListener { viewModel.incrementD100() }
        binding.btnRemoveD100.setOnClickListener { viewModel.decrementD100() }

        binding.btnClear.setOnClickListener { viewModel.clearAllDice() }
        binding.btnRoll.setOnClickListener { viewModel.rollDice() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
