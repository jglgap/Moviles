package com.example.to_do_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.to_do_list.databinding.FragmentResumeBinding
import kotlin.getValue


class ResumeFragment : Fragment() {

    private var _binding: FragmentResumeBinding? = null
    private val binding: FragmentResumeBinding
        get() = _binding!!
    val model: TasksModelView by viewModels(
        ownerProducer = { this.requireActivity() }
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentResumeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.getTotalTasks()
        model.getTotalofTasksCompleted()
        model.getTotalOfdificulty()

        model.totalTareas.observe(viewLifecycleOwner){
            total -> binding.total.text = "El total de tareas es = " + total.toString()
        }


        model.totalTareasCompletadas.observe(viewLifecycleOwner){
            total -> binding.completas.text ="El total de tareas completadas es = " + total.toString()
        }

        model.totalTareasDificiles.observe(viewLifecycleOwner){
            total -> binding.dificiles.text ="El total de tareas dificiles es = " + total.toString()
        }


        model.totalTareasMedianas.observe(viewLifecycleOwner){
            total -> binding.medias.text ="El total de tareas medianas es = " + total.toString()
        }

        model.totalTareasFaciles.observe(viewLifecycleOwner){
            total -> binding.faciles.text ="El total de tareas faciles es = " + total.toString()
        }
        binding.goHomeButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resumeFragment_to_addTasksFragment)
        }
        binding.goListButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resumeFragment_to_allTasksFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}