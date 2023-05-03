package com.example.healthtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.healthtrack.MenuActivity.Companion.Tickets_key

class ColecccionActivity : AppCompatActivity() {

    private lateinit var tvColeccionPuntos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colecccion)
        val Tickets:Int = intent.extras?.getInt(Tickets_key) ?: -2
        initComponents()
        initUI(Tickets)
    }

    private fun initComponents() {
        tvColeccionPuntos = findViewById(R.id.tvColeccionPuntos)
    }
    private fun initUI(valorPuntos: Int){
        tvColeccionPuntos.text = "Tus Tickets: $valorPuntos"
    }
}