package com.example.space

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.space.databinding.FragmentStarsBinding


class StarsFragment : Fragment() {
    private var _binding: FragmentStarsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.buttonLendas.setOnClickListener {
                findNavController().navigate(R.id.action_starsFragment_to_lendasFragment)
            }

         binding.buttonObservacion.setOnClickListener {
             findNavController().navigate(R.id.action_starsFragment_to_observacionFragment)
         }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}