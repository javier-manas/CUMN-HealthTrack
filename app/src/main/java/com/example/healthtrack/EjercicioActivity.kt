package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    private lateinit var viewEjCrearCuenta: CardView
    private lateinit var viewEjUpdate: CardView
    private lateinit var etEjUsuario: TextView
    private lateinit var etEjEjercicio: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ejercicio)
        val Tickets:Int = intent.extras?.getInt(MenuActivity.Tickets_key) ?: -2
        val Puntos:Int = intent.extras?.getInt(MenuActivity.Puntos_key) ?: -2
        val Correo:String = intent.extras?.getString(MenuActivity.Correo_key) ?: "-2"
        val Usuario:String = intent.extras?.getString(MenuActivity.Usuario_key) ?: "-2"
        val UsId:String = intent.extras?.getString(MenuActivity.UsId_key) ?: "-2"
        initComponents()
        initListeners(Usuario, UsId, Correo, Puntos, Tickets)
    }

    private fun initComponents() {
        viewEjCrearCuenta = findViewById(R.id.viewEjCrearCuenta)
        viewEjUpdate = findViewById(R.id.viewEjUpdate)
        etEjUsuario = findViewById(R.id.etEjUsuario)
        etEjEjercicio = findViewById(R.id.etEjEjercicio)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseBD = FirebaseDatabase.getInstance().getReference("HistorialBD")
    }

    private fun initListeners( Usuario: String, UsId: String, Correo: String, Puntos: Int, Tickets: Int) {
        val histID = firebaseBD.push().key!!

        viewEjCrearCuenta.setOnClickListener {
            val descripcion = etEjUsuario.text.toString()
            val ejercicio = etEjEjercicio.text.toString()
            crearCuenta(histID, descripcion, ejercicio, Usuario, UsId, Correo, Puntos, Tickets)
        }
        viewEjUpdate.setOnClickListener {
            val descripcion = etEjUsuario.text.toString()
            val ejercicio = etEjEjercicio.text.toString()
            updateData(histID, descripcion, ejercicio)
        }
    }

    private fun updateData( histID: String, descripcion: String, ejercicio: String) {
        val ejMod = "data mod " + ejercicio

        val histModel = historialModel(histID, descripcion, ejMod)
        firebaseBD.child(firebaseAuth.uid!!).child(histID).setValue(histModel)
    }

    private fun crearCuenta(histID: String, descripcion: String, ejercicio: String, Usuario: String, UsId: String, Correo: String, Puntos: Int, Tickets: Int) {

        if (ejercicio.isNotEmpty()){
            if (descripcion.isNotEmpty()){
                val histModel = historialModel(histID, descripcion, ejercicio)
                firebaseBD.child(firebaseAuth.uid!!).child(histID).setValue(histModel).addOnCompleteListener (this) { task ->
                    if (task.isSuccessful) {
                        aumentarPuntos(Usuario, UsId, Correo, Puntos, Tickets)
                    } else {
                        Toast.makeText(baseContext, "error al crear ej", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(baseContext,"el campo descripcion esta vacio", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(baseContext,"el campo ejercicio esta vacio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun aumentarPuntos(Usuario: String, UsId: String, Correo: String, Puntos: Int, Tickets: Int) {
        firebaseBD = FirebaseDatabase.getInstance().getReference("RegistrarseBD")
        val Puntosmod = Puntos + 1
        val Ticketsmod = Tickets + 1
        val usModel = UsuarioModel(UsId, Correo, Usuario, Puntosmod, Ticketsmod)
        firebaseBD.child(firebaseAuth.uid!!).setValue(usModel).addOnCompleteListener (this) { task ->
            if (task.isSuccessful) {

            } else {
                Toast.makeText(baseContext, "error al aumentar puntos", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun navigateToMain() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}