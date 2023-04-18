package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private var usuario: String = "True"

    private lateinit var viewIniciarSesion: CardView

    companion object{
        const val USARIO_KEY = "True"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        viewIniciarSesion = findViewById(R.id.viewIniciarSesion)

    }

    private fun initListeners() {
        viewIniciarSesion.setOnClickListener {
            navigateToMenu(usuario)
        }

    }

    private fun navigateToMenu(usuario: String) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(USARIO_KEY, usuario)
        startActivity(intent)
    }

}