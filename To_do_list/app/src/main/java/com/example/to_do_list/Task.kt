package com.example.to_do_list

import android.R
import androidx.lifecycle.MutableLiveData

data class Task(
    val idtask: Int,
    val taskName: String,
    val priority: String,
    val completed: Boolean
)