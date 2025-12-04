package com.example.to_do_list

import TaskAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_list.databinding.FragmentAddTasksBinding
import com.example.to_do_list.databinding.FragmentAllTasksBinding
import kotlin.getValue


class AllTasksFragment : Fragment(R.layout.fragment_all_tasks) {

    private var _binding: FragmentAllTasksBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TaskAdapter
    val model: TasksModelView by viewModels(
        ownerProducer = { requireActivity() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAllTasksBinding.bind(view)

        adapter = TaskAdapter(model)
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTasks.adapter = adapter

        model.listTasks.observe(viewLifecycleOwner) { tasks ->
            adapter.submitList(tasks)
        }

        binding.goHomeButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_allTasksFragment_to_addTasksFragment)
        }
        binding.goResumeButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_allTasksFragment_to_resumeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
