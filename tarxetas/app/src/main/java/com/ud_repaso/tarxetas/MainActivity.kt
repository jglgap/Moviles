package com.ud_repaso.tarxetas

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



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tigreImg = findViewById<ImageView>(R.id.tigre_img)
        val mapacheImg = findViewById<ImageView>(R.id.mapache_img)
        val animalInfo = findViewById<TextView>(R.id.animalInfo);
        val mapacheInfo = """
            Procyon es un género de mamíferos carnívoros de la familia Procyonidae​ conocidos comúnmente como mapaches u osos lavadores. Son propios de América.
        """.trimIndent()

        val tigreInfo = """
            El tigre es una de las especies​ de la subfamilia de los panterinos pertenecientes al género Panthera.
        """.trimIndent()

        tigreImg.setOnClickListener(){
            animalInfo.text = tigreInfo
        }

        mapacheImg.setOnClickListener(){
            animalInfo.text = mapacheInfo
        }

    }
}