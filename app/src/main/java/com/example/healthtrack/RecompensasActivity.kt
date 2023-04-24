package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.cardview.widget.CardView

class RecompensasActivity : AppCompatActivity() {

    private lateinit var ViewColeccion: CardView
    private lateinit var ViewCanjear: CardView
    private lateinit var tvPuntos: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recompensas)
        val valorPuntos:Int = 0
        initComponents()
        initUI(valorPuntos)
        initListeners()
    }

    private fun initComponents() {
        ViewColeccion = findViewById(R.id.ViewColeccion)
        ViewCanjear = findViewById(R.id.ViewCanjear)
        tvPuntos = findViewById(R.id.tvPuntos)
    }

    private fun initListeners() {
        ViewColeccion.setOnClickListener {
            navigateColeccion()
        }

        ViewCanjear.setOnClickListener {
            navigateCanjear()
        }

    }

    private fun navigateColeccion() {
        val intent = Intent(this, ColecccionActivity::class.java)
        startActivity(intent)
    }

    private fun navigateCanjear() {
        val intent = Intent(this, CanjearActivity::class.java)
        startActivity(intent)
    }

    private fun initUI(valorPuntos: Int){
        tvPuntos.text = valorPuntos.toString()
    }
}