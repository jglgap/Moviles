package com.fargmentos.fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TextFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_text, container, false)
        val text = view.findViewById<EditText>(R.id.text_fragment_TextView).text

        val btn = view.findViewById<Button>(R.id.encriptarButton)

        btn.setOnClickListener{
            val encryptedText = caesarCipherROTN(text.toString(), 13)
            val navigationInstruction = TextFragmentDirections.actionTextFragmentToFinalFragment(encryptedText)
            btn.findNavController().navigate(navigationInstruction)
        }

        return view
    }

    private fun caesarCipherROTN(input: String, n: Int): String {
        val shift = n % 26
        return input.map { char ->
            when (char) {
                in 'A'..'Z' -> 'A' + (char - 'A' + shift) % 26
                in 'a'..'z' -> 'a' + (char - 'a' + shift) % 26
                else -> char
            }
        }.joinToString("")
    }

}