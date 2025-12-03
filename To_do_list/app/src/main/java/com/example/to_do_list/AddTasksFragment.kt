package com.example.to_do_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.core.view.children
import androidx.fragment.app.viewModels
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

        val name = binding.taskName.text.toString()

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val selectedChips = group.children
                .filterIsInstance<Chip>()
                .filter { it.isChecked }
                .map { it.text.toString() }

            Toast.makeText(this, "Selected: $selectedChips", Toast.LENGTH_SHORT).show()
        }


        binding.createTaskButton.setOnClickListener {  v ->
            model.addTask(name,"",false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}