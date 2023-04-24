package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class ImcActivity : AppCompatActivity() {

    private lateinit var ViewVerMiImc: CardView
    private lateinit var ViewActualizarMiImc: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        ViewVerMiImc = findViewById(R.id.ViewVerTuImc)
        ViewActualizarMiImc = findViewById(R.id.ViewCambiarMiImc)

    }

    private fun initListeners() {
        ViewVerMiImc.setOnClickListener {
            navigateToVerMiImc()
        }

        ViewActualizarMiImc.setOnClickListener {
            navigateToActualizarMiImc()
        }


    }

    private fun navigateToVerMiImc() {
        val intent = Intent(this, VerMiImcActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToActualizarMiImc() {
        val intent = Intent(this, ActualizarMiImcActivity::class.java)
        startActivity(intent)
    }
}