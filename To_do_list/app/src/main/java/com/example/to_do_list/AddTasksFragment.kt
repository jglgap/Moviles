package com.example.to_do_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.to_do_list.databinding.FragmentAddTasksBinding
import com.google.android.material.chip.Chip

class AddTasksFragment : Fragment() {

    private var _binding: FragmentAddTasksBinding? = null
    private val binding: FragmentAddTasksBinding
        get() = _binding!!

    val model: TasksModelView by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTasksBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        var priority: String? = null

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chipId = checkedIds[0]
                val chip = group.findViewById<Chip>(chipId)
                priority = chip.text.toString()
            } else {
                priority = null
            }
        }


        binding.createTaskButton.setOnClickListener {  v ->
            var nameOfTask = binding.taskNameField.text.toString()

            if (priority == null){
                Toast.makeText(requireContext(),"Please pick a priority", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            model.addTask(nameOfTask,priority.toString(),false)
            binding.taskNameField.text.clear()
        }

        binding.goListButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_addTasksFragment_to_allTasksFragment)
        }
        binding.goResumeButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_addTasksFragment_to_resumeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}