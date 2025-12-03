package com.example.to_do_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TasksModelView : ViewModel() {

    var listTasks = MutableLiveData<List<Task>>(emptyList()) // start with empty list

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

}