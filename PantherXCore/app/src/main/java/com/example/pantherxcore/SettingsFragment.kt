package com.example.pantherxcore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pantherxcore.databinding.FragmentSettingsBinding
import kotlinx.coroutines.launch


class SettingsFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // ViewModel compartido con toda la Activity
    private val viewModel: TrainingViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        // Listener para el RadioGroup
        binding.radioGroupTheme.setOnCheckedChangeListener { _, checkedId ->
            // Convertir el ID del RadioButton a un valor de opción
            val option = when (checkedId) {
                R.id.radioPurpura -> 1
                R.id.radioAmarillo -> 2
                R.id.radioNaranja -> 3
                R.id.radioGris -> 4
                else -> 0
            }
            viewModel.setSelectedColorOption(option)
        }

        // Listener para el Switch
        binding.switchBackground.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setBackgroundColorChanged(isChecked)
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeViewModel() {
        // Observar el estado del switch
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isBackgroundColorChanged.collect { isChanged ->
                binding.switchBackground.isChecked = isChanged
                updateCardColor()
            }
        }

        // Observar la opción de color seleccionada
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedColorOption.collect { option ->
                // Actualizar el RadioButton seleccionado
                val radioButtonId = when (option) {
                    1 -> R.id.radioPurpura
                    2 -> R.id.radioAmarillo
                    3 -> R.id.radioNaranja
                    4 -> R.id.radioGris
                    else -> -1
                }
                if (radioButtonId != -1 && binding.radioGroupTheme.checkedRadioButtonId != radioButtonId) {
                    binding.radioGroupTheme.check(radioButtonId)
                }
                updateCardColor()
            }
        }
        lifecycleScope.launch {
            viewModel.isBackgroundColorChanged.collect { isChanged ->
                updateSwitchColor(isChanged)
            }
        }
    }

    private fun updateCardColor() {
        val color = getBaseColorFromOption()

        // Cambiar el color del CardView
        (binding.root as? com.google.android.material.card.MaterialCardView)?.setCardBackgroundColor(color)
    }

    private fun getBaseColorFromOption(): Int {
        val colorRes = when (viewModel.selectedColorOption.value) {
            1 -> R.color.purpura  // Púrpura
            2 -> R.color.amarillo // Amarillo
            3 -> R.color.orange   // Naranja
            4 -> R.color.gray     // Gris
            else -> R.color.purpura // Por defecto púrpura
        }
        return ContextCompat.getColor(requireContext(), colorRes)
    }
    private fun updateSwitchColor(isChanged: Boolean) {
        val colorRes = if (isChanged) {
            R.color.whiter // Color cuando el switch está ON
        } else {
            R.color.darker     // Color cuando el switch está OFF
        }

        val backgroundColorRes = if (isChanged) {
            R.color.darker  // Color cuando el switch está ON
        } else {
            R.color.whiter     // Color cuando el switch está OFF
        }
        binding.backButton.setBackgroundColor(ContextCompat.getColor(requireContext(), backgroundColorRes))
        binding.backButton.setTextColor(ContextCompat.getColor(requireContext(), colorRes));
    }

}