package com.example.pantherxcore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pantherxcore.databinding.FragmentExerciseListBinding
import kotlinx.coroutines.launch

class ExerciseListFragment : Fragment() {

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrainingViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private lateinit var adapter: ExerciseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = ExerciseAdapter(
            onExerciseClick = { exercise ->
                // Acción al hacer clic en un ejercicio
            },
            selectedColorOption = viewModel.selectedColorOption.value,
            isBackgroundChanged = viewModel.isBackgroundColorChanged.value  // 🔹 Pasar el estado inicial
        )

        binding.recyclerViewExercises.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ExerciseListFragment.adapter
        }

        updateExerciseList()
        updateBackgroundColor()
    }

    private fun observeViewModel() {
        // 🔹 Observar cambios de color del tema
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedColorOption.collect { colorOption ->
                updateAdapter(colorOption, viewModel.isBackgroundColorChanged.value)
            }
        }

        // 🔹 Observar cambios del switch de fondo
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isBackgroundColorChanged.collect { isChanged ->
                updateBackgroundColor()
                updateAdapter(viewModel.selectedColorOption.value, isChanged)
            }
        }
    }

    // 🔹 Nueva función para actualizar el adapter con ambos parámetros
    private fun updateAdapter(colorOption: Int, isBackgroundChanged: Boolean) {
        adapter = ExerciseAdapter(
            onExerciseClick = { exercise ->
                // Acción al hacer clic
            },
            selectedColorOption = colorOption,
            isBackgroundChanged = isBackgroundChanged
        )
        binding.recyclerViewExercises.adapter = adapter
        updateExerciseList()
    }

    private fun updateExerciseList() {
        val exercisesWithHeaders = viewModel.getExercisesWithHeaders()
        adapter.submitList(exercisesWithHeaders)
    }

    private fun updateBackgroundColor() {
        val backgroundColorRes = if (viewModel.isBackgroundColorChanged.value) {
            R.color.darker  // Fondo oscuro cuando el switch está ON
        } else {
            R.color.whiter// Fondo normal cuando el switch está OFF
        }
        binding.root.setBackgroundColor(
            ContextCompat.getColor(requireContext(), backgroundColorRes)
        )

    }
}