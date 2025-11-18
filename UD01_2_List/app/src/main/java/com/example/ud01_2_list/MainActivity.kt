package com.example.ud01_2_list

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val searchBtn = findViewById<Button>(R.id.animalSelect);
        // Better to get reference at initialization and then use it or just to get them when the
        // event listener is called so they can be disposed? Getting out is kind of lifting state-up?
        //
            val spinnerOpts = findViewById<Spinner>(R.id.animalType)
            val displayText = findViewById<TextView>(R.id.animalSelected)


        searchBtn.setOnClickListener {
            displayText.text = getListOfAnimals(spinnerOpts.selectedItemId).joinToString("\n");
        }

    }


    private fun getListOfAnimals(id: Long) : List<String> = when(id) {
        0L -> listOf("Pitbull", "Dobberman", "Samoyed")
        1L -> listOf("Gato naranja", "Gato negro", "Gato calvo")
        2L -> listOf("Pájaro carpintero", "Faisán", "Águila")
        else -> listOf()
    }
}