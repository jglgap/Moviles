package com.example.uf1_ud06_3_guessgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.uf1_ud06_3_guessgame.databinding.FragmentGameBinding
import com.google.android.material.snackbar.Snackbar

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    val model: GameViewModel by viewModels(
        ownerProducer = { this.requireActivity() }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameObserver = Observer<String> { newName ->
            // Actualizar la interfaz de usuario, en este caso, un TextView.
            binding.txtWord.text = newName
        }
        model.secretWordDisplay.observe(viewLifecycleOwner,nameObserver)

        val livesObserver = Observer<Int> { newName ->
            // Actualizar la interfaz de usuario, en este caso, un TextView.
            binding.txtLives.text = "Quedanche $newName vidas"
        }
        model.lives.observe(viewLifecycleOwner,livesObserver)

        model.clearInput.observe(viewLifecycleOwner){
            binding.txtGuess.text.clear()
        }
        //updateScreen()

        binding.buttonNext.setOnClickListener {
            //model.secretWord = "Prueba de modelo"
            if(binding.txtGuess.text.length>0){
                //Comprobar la letra introducida
                model.makeGuess(binding.txtGuess.text.toString())
                //Actualizamos la pantalla
             //   updateScreen()
                //Si acertamos la palabra o nos quedamos sin vidas
                if (model.win() || model.lost())
                    view.findNavController().navigate(R.id.action_gameFragment_to_resultFragment)
            }else{
                //Sino se introduce ningún texto mostramos un aviso
                Snackbar.make(view, "Introduce una letra", Snackbar.LENGTH_SHORT).show()
            }
        }

    }

   // fun updateScreen(){
      //  binding.txtWord.text = model.secretWordDisplay
     //   binding.txtLives.text = "Te quedan ${model.lives} vidas"
    //    binding.txtGuess.text = null
  //  }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}