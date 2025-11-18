package com.tareas.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [Onboarding1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Onboarding1Fragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboarding1, container, false)
        val button = view.findViewById<Button>(R.id.discover_btn)
        button.setOnClickListener{
            view.findNavController().navigate(R.id.action_onboarding1Fragment_to_onboarding2Fragment)
        }
        return  view
    }

}