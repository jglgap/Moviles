package com.example.space

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class StarsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stars, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            val button_lendas = view.findViewById<Button>(R.id.button_lendas)
            button_lendas.setOnClickListener {
                findNavController().navigate(R.id.action_starsFragment_to_lendasFragment)
            }

            val button_observacion = view.findViewById<Button>(R.id.button_observacion)
            button_observacion.setOnClickListener {
                findNavController().navigate(R.id.action_starsFragment_to_observacionFragment)
            }
    }
}