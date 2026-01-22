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
enum class ExerciseType(val displayName: String) {
    PECTORAL("Ejercicios pectoral"),
    ESPALDA("Ejercicios espalda"),
    BICEPS("Ejercicios bíceps"),
    TRICEPS("Ejercicios tríceps"),
    HOMBROS("Ejercicios hombros"),
    PIERNAS("Ejercicios piernas")
}

// Modelo de datos para ejercicios
data class Exercise(
    val id: Int,
    val name: String,
    val type: ExerciseType,
    val imageRes: Int
)

// Sealed class para los items de la lista (headers + ejercicios)
sealed class ExerciseListItem {
    data class Header(val title: String, val type: ExerciseType) : ExerciseListItem()
    data class ExerciseItem(val exercise: Exercise) : ExerciseListItem()
}
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


    // Lista estática de ejercicios
    private val _exercises = MutableStateFlow<List<Exercise>>(
        listOf(
            // Ejercicios pectorales
            Exercise(1, "Press banca", ExerciseType.PECTORAL, R.drawable.press_banca),
            Exercise(2, "Press con mancuerna", ExerciseType.PECTORAL, R.drawable.banca_mancuernas),
            Exercise(3, "Cruce de poleas", ExerciseType.PECTORAL, R.drawable.cruce_poleas),

            // Ejercicios espalda
            Exercise(4, "Jalón al pecho", ExerciseType.ESPALDA, R.drawable.jalon_pecho),
            Exercise(5, "Remo con barra", ExerciseType.ESPALDA, R.drawable.remo_barra),

            // Ejercicios bíceps
            Exercise(6, "Curl martillo", ExerciseType.BICEPS, R.drawable.curl_martillo),
            Exercise(7, "Curl de biceps martillo", ExerciseType.BICEPS, R.drawable.curl_biceps),

            // Ejercicios triceps
            Exercise(8, "Paralelas", ExerciseType.TRICEPS, R.drawable.paralelas),
            Exercise(9, "Extension en poleas", ExerciseType.TRICEPS, R.drawable.extension_poleas),

            //Ejercicios hombros
            Exercise(10, "Elevacion frontal", ExerciseType.HOMBROS, R.drawable.elevacion_frontal),
            Exercise(11, "Elevacion lateral", ExerciseType.HOMBROS, R.drawable.elevacion_lateral),
            Exercise(12, "Press militar", ExerciseType.HOMBROS, R.drawable.press_militar),
            //Ejercicios pierna
            Exercise(13, "Sentadilla libre", ExerciseType.PIERNAS, R.drawable.sentadilla_libre),
            Exercise(14, "Prensa", ExerciseType.PIERNAS, R.drawable.prensa),
            Exercise(15, "Extensión de cuadriceps", ExerciseType.PIERNAS, R.drawable.extension_cuadriceps),

        )
    )
    val exercises: StateFlow<List<Exercise>> = _exercises.asStateFlow()

    // Función para obtener la lista con headers
    fun getExercisesWithHeaders(): List<ExerciseListItem> {
        val items = mutableListOf<ExerciseListItem>()

        // Agrupar ejercicios por tipo
        val groupedExercises = _exercises.value.groupBy { it.type }

        // Para cada tipo, agregar header y ejercicios
        ExerciseType.values().forEach { type ->
            val exercisesOfType = groupedExercises[type]
            if (!exercisesOfType.isNullOrEmpty()) {
                // Agregar header
                items.add(ExerciseListItem.Header(type.displayName, type))
                // Agregar ejercicios
                exercisesOfType.forEach { exercise ->
                    items.add(ExerciseListItem.ExerciseItem(exercise))
                }
            }
        }

        return items
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