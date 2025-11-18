package com.ud_repaso.comment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        
        val commentResult = findViewById<TextView>(R.id.commentResult)
        val commentText = findViewById<EditText>(R.id.commentText).text
        val nameText = findViewById<EditText>(R.id.nameText).text

        val button = findViewById<Button>(R.id.sendBtn)

        button.setOnClickListener{
            commentResult.text = buildString {
                append(commentResult.text)
                append("[")
                append(nameText)
                append("]:")
                append(commentText)
                append("\n")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}