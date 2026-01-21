package com.example.pantherxcore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pantherxcore.databinding.FragmentAddTrainingBinding
import kotlinx.coroutines.launch

class AddTrainingFragment : Fragment() {

    private var _binding: FragmentAddTrainingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrainingViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTrainingBinding.inflate(inflater, container, false)
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
        // Listener para el botón "Añadir"
        binding.addButton.setOnClickListener {
            val name = binding.etTrainingName.text.toString().trim()
            val description = binding.etTrainingDescription.text.toString().trim()

            if (name.isNotEmpty()) {
                // Agregar el entrenamiento al ViewModel
                viewModel.addTraining(name, description)

                // Opcional: limpiar campos o notificar éxito
                // Por ejemplo, podrías mostrar un Toast aquí si lo deseas

                // Volver atrás después de añadir
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                binding.etTrainingName.error = "El nombre es obligatorio"
            }
        }

        // Listener para el botón "Volver" (solo vuelve atrás, sin guardar)
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeViewModel() {
        // Observar el estado del switch para actualizar colores del botón
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isBackgroundColorChanged.collect { isChanged ->
                updateButtonColor(isChanged)
            }
        }

        // Observar la opción de color seleccionada para actualizar la CardView
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedColorOption.collect { option ->
                updateCardColor(option)
            }
        }
    }

    private fun updateCardColor(option: Int) {
        val colorRes = when (option) {
            1 -> R.color.purpura  // Púrpura
            2 -> R.color.amarillo // Amarillo
            3 -> R.color.orange   // Naranja
            4 -> R.color.gray     // Gris
            else -> R.color.purpura // Por defecto púrpura
        }

        val color = ContextCompat.getColor(requireContext(), colorRes)

        // Cambiar el color del CardView
        (binding.root as? com.google.android.material.card.MaterialCardView)?.setCardBackgroundColor(color)
    }

    private fun updateButtonColor(isChanged: Boolean) {
        val textColorRes = if (isChanged) {
            R.color.whiter // Color del texto cuando el switch está ON
        } else {
            R.color.darker // Color del texto cuando el switch está OFF
        }

        val backgroundColorRes = if (isChanged) {
            R.color.darker // Color de fondo cuando el switch está ON
        } else {
            R.color.whiter // Color de fondo cuando el switch está OFF
        }

        // Actualizar el botón
        binding.backButton.setBackgroundColor(
            ContextCompat.getColor(requireContext(), backgroundColorRes)
        )
        binding.addButton.setBackgroundColor(
            ContextCompat.getColor(requireContext(), backgroundColorRes)
        )
        binding.backButton.setTextColor(
            ContextCompat.getColor(requireContext(), textColorRes)
        )
        binding.addButton.setTextColor(
            ContextCompat.getColor(requireContext(), textColorRes)
        )
    }
}