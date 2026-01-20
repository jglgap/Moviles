package com.example.pantherxcore

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Modelo de datos para los entrenamientos
data class Training(
    val id: Int,
    val name: String,
    val description: String
)
class TrainingViewModel : ViewModel(){
    // Variable booleana para cambiar el color de fondo
    private val _isBackgroundColorChanged = MutableStateFlow(false)
    val isBackgroundColorChanged: StateFlow<Boolean> = _isBackgroundColorChanged.asStateFlow()

    // Variable para el color seleccionado del RadioGroup
    // Puedes usar un Int para almacenar el ID del RadioButton seleccionado
    private val _selectedColorOption = MutableStateFlow(0)
    val selectedColorOption: StateFlow<Int> = _selectedColorOption.asStateFlow()

    // Lista de entrenamientos
    private val _trainings = MutableStateFlow<List<Training>>(emptyList())
    val trainings: StateFlow<List<Training>> = _trainings.asStateFlow()

    // Contador para generar IDs únicos
    private var nextId = 1

    // Función para cambiar el estado del color de fondo
    fun toggleBackgroundColor() {
        _isBackgroundColorChanged.value = !_isBackgroundColorChanged.value
    }

    // Función para establecer el color de fondo directamente
    fun setBackgroundColorChanged(isChanged: Boolean) {
        _isBackgroundColorChanged.value = isChanged
    }

    // Función para actualizar la opción de color seleccionada del RadioGroup
    fun setSelectedColorOption(optionId: Int) {
        _selectedColorOption.value = optionId
    }

    // Función para obtener el color basado en la opción seleccionada
    fun getColorFromOption(): Int {
        return when (_selectedColorOption.value) {
            1 -> android.graphics.Color.RED
            2 -> android.graphics.Color.GREEN
            3 -> android.graphics.Color.BLUE
            4 -> android.graphics.Color.YELLOW
            else -> android.graphics.Color.WHITE
        }
    }

    // Función para agregar un entrenamiento
    fun addTraining(name: String, description: String) {
        val newTraining = Training(
            id = nextId++,
            name = name,
            description = description
        )
        _trainings.value = _trainings.value + newTraining
    }

    // Función para eliminar un entrenamiento por ID
   // fun removeTraining(id: Int) {
    //    _trainings.value = _trainings.value.filter { it.id != id }
   // }

    // Función para actualizar un entrenamiento
    //fun updateTraining(id: Int, name: String, description: String) {
      //  _trainings.value = _trainings.value.map { training ->
        //    if (training.id == id) {
            //    training.copy(name = name, description = description)
        //    } else {
             //   training
            // }
    //}

    // Función para obtener un entrenamiento por ID
 /*   fun getTrainingById(id: Int): Training? {
        return _trainings.value.find { it.id == id }
    }

    // Función para limpiar todos los entrenamientos
    fun clearTrainings() {
        _trainings.value = emptyList()
        nextId = 1
    }*/
}