package com.fargmentos.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import java.time.InstantSource

class FinalFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_final, container, false)

        val txtView = view.findViewById<TextView>(R.id.finalFragment_text)
        txtView.text = FinalFragmentArgs.fromBundle(requireArguments()).encryptedText;
        return  view
    }

}