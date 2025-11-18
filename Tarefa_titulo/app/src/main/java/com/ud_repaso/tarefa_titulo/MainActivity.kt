package com.ud_repaso.tarefa_titulo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    // Wouldn't I want to create a resource out of this ?
    private val listOfAdjectives = mutableListOf("O misterio de", "A vinganza de", "o ultimo baile para");
    private val listOfPlaces = mutableListOf("o robot perdido", "a illa esquecida", "o dragón de xeo");
    private val listOfTimes = mutableListOf("no verán do 87", "antes de media noite", "sen voltar atras");

    private fun getRandomStringOfCollection(mutableList : MutableList<String>) : String{
        val rand  = Random.nextInt(0, mutableList.size);
        return  mutableList.get(rand);
    }

    private fun generateRandomStory() : String {
        return getRandomStringOfCollection(listOfAdjectives) +
                getRandomStringOfCollection(listOfTimes) +
                getRandomStringOfCollection(listOfPlaces)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val changeStoryButton = findViewById<Button>(R.id.storyBtn);
        val storyTextView = findViewById<TextView>(R.id.storyField);

        changeStoryButton.setOnClickListener(){
            storyTextView.text = generateRandomStory();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}