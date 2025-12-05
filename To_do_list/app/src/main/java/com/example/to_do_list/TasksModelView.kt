package com.example.to_do_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TasksModelView : ViewModel() {

    var listTasks = MutableLiveData<List<Task>>(emptyList()) // start with empty list

    var totalTareas  = MutableLiveData<Int>();
    var totalTareasCompletadas = MutableLiveData<Int>()
    var totalTareasDificiles = MutableLiveData<Int>()
    var totalTareasMedianas = MutableLiveData<Int>()
    var totalTareasFaciles = MutableLiveData<Int>()
    // Method to create and add a new task
    fun addTask(taskName: String, priority: String, completed: Boolean = false) {
        // Generate a new ID (you can improve this logic if needed)
        val newId = (listTasks.value?.maxOfOrNull { it.idtask } ?: 0) + 1

        val newTask = Task(
            idtask = newId,
            taskName = taskName,
            priority = priority,
            completed = completed
        )

        // Add the new task to the current list
        val updatedList = listTasks.value.orEmpty().toMutableList()
        updatedList.add(newTask)

        // Post the updated list
        listTasks.value = updatedList
    }


    fun toggleTaskCompleted(taskId: Int) {
        val updatedList = listTasks.value.orEmpty().map { task ->
            if (task.idtask == taskId) {
                task.copy(completed = !task.completed) // Cambia completed a su opuesto
            } else {
                task
            }
        }
        listTasks.value = updatedList
    }


    fun getTotalTasks(){
        totalTareas.value = listTasks.value?.size ?: 0
    }


    fun getTotalofTasksCompleted(){
        totalTareasCompletadas.value = listTasks.value?.count { it.completed } ?: 0
    }

    fun getTotalOfdificulty(){
        totalTareasDificiles.value = listTasks.value?.count { it.priority.lowercase() == "hard" } ?: 0
        totalTareasMedianas.value = listTasks.value?.count { it.priority.lowercase() == "medium" } ?: 0
        totalTareasFaciles.value = listTasks.value?.count { it.priority.lowercase() == "easy" } ?: 0
    }
}