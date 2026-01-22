package com.example.pantherxcore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.jvm.java

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Duración del splash (2 segundos)
        Handler(Looper.getMainLooper()).postDelayed({
            // Abrir MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Evita que el usuario pueda volver al splash
        }, 2500) //
    }
}