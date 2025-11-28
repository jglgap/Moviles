package com.example.velocidadmedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.velocidadmedia.databinding.FragmentResultBinding
import kotlin.getValue



class ResultFragment : Fragment() {


    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    val model: VelocidadModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.resulatdo.text = String.format("%.2f m/s", model.resultado.value ?: 0.0)
        binding.resulatdoMs.text = String.format("%.2f m/s", model.resultadoMs.value ?: 0.0)

        binding.volverButton.setOnClickListener {
            model.restart()
            view.findNavController().navigate(R.id.action_resultFragment_to_calculadorFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}