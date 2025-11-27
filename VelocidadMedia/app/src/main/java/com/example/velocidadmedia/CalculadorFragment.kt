package com.example.velocidadmedia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.velocidadmedia.databinding.FragmentCalculadorBinding


class CalculadorFragment : Fragment() {


    private var _binding: FragmentCalculadorBinding? = null
    private val binding get() = _binding!!

    val model: VelocidadModel by viewModels {
        ownerProducer = { this.requireActivity() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculadorBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calcularButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_calculadorFragment_to_resultFragment)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}