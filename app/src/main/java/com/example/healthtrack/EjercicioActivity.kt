package com.example.healthtrack

import android.annotation.SuppressLint
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
import com.google.firebase.database.*

class EjercicioActivity : AppCompatActivity() {


    private lateinit var firebaseBD: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var btnAñadirEj: Button
    private lateinit var tvEjActual: TextView
    private var Puntos: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio)
        initComponents()
        getData()
        initListeners()
    }

    private fun getData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("RegistrarseBD/" + firebaseAuth.uid!! )
        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(UsuarioModel::class.java)
                Puntos = userProfile?.Puntos!!
                initUI()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"cancel", Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun initUI() {
        if (Puntos > 0){
            tvEjActual.text = "air bike"
        }else{
            tvEjActual.text = "no hay ningun ejercicio realizandose actualmente"
        }
    }

    private fun initComponents() {
        btnAñadirEj = findViewById(R.id.btnAñadirEj)
        tvEjActual = findViewById(R.id.tvEjActual)
        firebaseAuth = FirebaseAuth.getInstance()


    }

    private fun initListeners( ) {

        btnAñadirEj.setOnClickListener {
           navigateToApi()
        }
    }

    private fun navigateToApi() {
        val intent = Intent(this, ApiEjActivity::class.java)
        startActivity(intent)
    }
}