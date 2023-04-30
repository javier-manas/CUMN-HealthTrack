package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private lateinit var viewEjCrearCuenta: CardView
    private lateinit var etEjUsuario: TextView
    private lateinit var etEjEjercicio: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        viewEjCrearCuenta = findViewById(R.id.viewEjCrearCuenta)
        etEjUsuario = findViewById(R.id.etEjUsuario)
        etEjEjercicio = findViewById(R.id.etEjEjercicio)
        firebaseBD = FirebaseDatabase.getInstance().getReference("HistorialBD")
    }

    private fun initListeners() {
        viewEjCrearCuenta.setOnClickListener {
            crearCuenta()
        }
    }

    private fun crearCuenta() {

        val usuario = etEjUsuario.text.toString()
        val ejercicio = etEjEjercicio.text.toString()
        if (ejercicio.isNotEmpty()){

            if (usuario.isNotEmpty()){



                            uploadData(usuario, ejercicio)
                            navigateToMain()




            }
            else
            {
                Toast.makeText(baseContext,"el campo usuario esta vacio", Toast.LENGTH_SHORT).show()
            }

        }
        else
        {
            Toast.makeText(baseContext,"el campo ejercicio esta vacio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData(usuario: String, ejercicio: String) {
        val histID = firebaseBD.push().key!!
        val histModel = historialModel(histID, usuario, ejercicio)
        firebaseBD.child(histID).setValue(histModel)
            .addOnCompleteListener {
                Toast.makeText(baseContext,"admin: data uploaded", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {err ->
                Toast.makeText(baseContext,"admin: data not uploaded: ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}