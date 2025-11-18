package com.example.uf1_ud06_3_guessgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // Seleccionamos una palabra aleatoria de esta lista de palabras
    val words = listOf("Android", "Fragment", "Kotlin", "Model")
    var secretWord  = words.random().uppercase()
    // String que se mostrará en la pantalla (guiones y letras a medida que las vamos descubriendo)
    var secretWordDisplay = MutableLiveData<String>()
    // Intentos del usuario. Caracteres que vaya probando el usuario.
    var guesses = mutableListOf<Char>()
    // Vidas
    var lives = MutableLiveData<Int>(8)
    val clearInput = MutableLiveData<Unit>()

    init {
        // Inicializamos la palabra con _
        secretWordDisplay.value = generateSecretWordDisplay()
    }

    // Genera la representación visual de la palabra oculta
    fun generateSecretWordDisplay() =
        // Recorremos cada uno de los caracteres de la palabra
        secretWord.map {
            // Si el caracter está en la lista, lo añadimos; sino, continuamos con _
            if (it in guesses) it
            else '_'
        }.joinToString("")

    // Realiza un intento de adivinanza por parte del usuario
    fun makeGuess(guess: String){
        if(guess.length > 0) {
            // Extraemos la letra inicial (aunque solo nos pueden introducir un caracter)
            val letter = guess.uppercase()[0]
            // La añadimos a la lista de intentos
            guesses.add(letter)

            secretWordDisplay.value = generateSecretWordDisplay()
            if(!secretWord.contains(letter))
                lives.value = lives.value?.minus(1)
            clearInput.value = Unit
        }
    }

    // Función para verificar si ganamos
    fun win() = secretWord == secretWordDisplay.value
    // Función para comprobar si nos quedan vidas
    fun lost() = (lives.value ?:0) <= 0


    fun restart() {
        guesses.clear()
        lives.value = 8
        secretWord = words.random().uppercase()
        secretWordDisplay.value = generateSecretWordDisplay()
    }

    fun resultMessage() =
        if (win()) "Ganaste!\n  La palabra secreta era $secretWord"
        else  "Oops, perdiste!\n  La palabra secreta era $secretWord"
}