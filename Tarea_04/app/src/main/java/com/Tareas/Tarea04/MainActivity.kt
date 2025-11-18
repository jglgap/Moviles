package com.Tareas.Tarea04

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val santiagoImage = findViewById<ImageView>(R.id.santiago_image)
        val ourenseImage = findViewById<ImageView>(R.id.ourense_image)
        val descText = findViewById<TextView>(R.id.description)

        santiagoImage.setOnClickListener(){
           descText.text = getString(R.string.la_catedral_de_santiago_de_compostela)
        }

        ourenseImage.setOnClickListener{
            descText.text = getString(R.string.una_ciudad_de_termas)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}