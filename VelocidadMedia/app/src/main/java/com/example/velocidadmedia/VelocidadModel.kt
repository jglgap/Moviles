package com.example.velocidadmedia

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VelocidadModel : ViewModel() {

    val velocidad = MutableLiveData<Double>()
    val horas = MutableLiveData<Double>()
    val resultado = MutableLiveData<Double>()


    fun calculate() {
        val v = velocidad.value ?: 0.0
        val h = horas.value ?: 0.0
        resultado.value = v + h

    }

    fun restart(){
        velocidad.value = 0.0
        horas.value = 0.0
        resultado.value = 0.0

    }
}