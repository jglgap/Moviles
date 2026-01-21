package com.example.pantherxcore

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pantherxcore.databinding.FragmentTrainingListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class TrainingListFragment : Fragment() {

    private var _binding: FragmentTrainingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TrainingViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    private lateinit var adapter: TrainingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFab()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = TrainingAdapter(
            onDetailsClick = { training -> showTrainingDetails(training) },
            selectedColorOption = viewModel.selectedColorOption.value ?: 1,
            isButtonColorChanged = viewModel.isBackgroundColorChanged.value ?: false
        )

        binding.recyclerViewTrainings.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TrainingListFragment.adapter
        }
    }

    private fun setupFab() {
        val colorRes = when (viewModel.selectedColorOption.value ?: 1) {
            1 -> R.color.purpura
            2 -> R.color.amarillo
            3 -> R.color.orange
            4 -> R.color.gray
            else -> R.color.purpura
        }

        val color = ContextCompat.getColor(requireContext(), colorRes)
        binding.fabAddTraining.backgroundTintList = ColorStateList.valueOf(color)
        binding.fabAddTraining.setOnClickListener {
            findNavController().navigate(R.id.action_trainingListFragment_to_addTrainingFragment)
        }
    }

    private fun observeViewModel() {

        // Observar lista de trainings
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.trainings.collect { trainings ->
                adapter.submitList(trainings)
            }
        }

        // Observar cambios de color del tema
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedColorOption.collect { colorOption ->
                adapter.updateSelectedColor(colorOption)
            }
        }

        // Observar variable que controla color del botón
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isBackgroundColorChanged.collect { isChanged ->
                adapter.updateButtonColorState(isChanged)
            }
        }
    }

    private fun showAddTrainingDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_add_training, null)

        val etName = dialogView.findViewById<TextInputEditText>(R.id.etTrainingName)
        val etDescription = dialogView.findViewById<TextInputEditText>(R.id.etTrainingDescription)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar Entrenamiento")
            .setView(dialogView)
            .setPositiveButton("Agregar") { _, _ ->
                val name = etName.text.toString()
                val description = etDescription.text.toString()
                if (name.isNotBlank()) viewModel.addTraining(name, description)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showTrainingDetails(training: Training) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(training.name)
            .setMessage(training.description)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}