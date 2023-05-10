package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.healthtrack.models.UsuarioModel
import com.example.healthtrack.models.historialModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EjercicioActivity : AppCompatActivity() {


    private lateinit var firebaseBD: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var btnAñadirEj: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio)
        val Tickets:Int = intent.extras?.getInt(MenuActivity.Tickets_key) ?: -2
        val Puntos:Int = intent.extras?.getInt(MenuActivity.Puntos_key) ?: -2
        val Correo:String = intent.extras?.getString(MenuActivity.Correo_key) ?: "-2"
        val Usuario:String = intent.extras?.getString(MenuActivity.Usuario_key) ?: "-2"
        val UsId:String = intent.extras?.getString(MenuActivity.UsId_key) ?: "-2"
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        btnAñadirEj = findViewById(R.id.btnAñadirEj)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseBD = FirebaseDatabase.getInstance().getReference("HistorialBD")
    }

    private fun initListeners( ) {
        val histID = firebaseBD.push().key!!

        btnAñadirEj.setOnClickListener {
           navigateToApi()
        }
    }

    private fun navigateToApi() {
        val intent = Intent(this, ApiEjActivity::class.java)
        startActivity(intent)
    }
}