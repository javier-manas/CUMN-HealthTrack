package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.healthtrack.models.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseBD: DatabaseReference

    private lateinit var viewRegCrearCuenta: CardView
    private lateinit var etRegUsuario: TextView
    private lateinit var etRegEmailAddress: TextView
    private lateinit var etRegPassword: TextView
    private lateinit var etRegConfirmar: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        viewRegCrearCuenta = findViewById(R.id.viewRegCrearCuenta)
        etRegUsuario = findViewById(R.id.etRegUsuario)
        etRegEmailAddress = findViewById(R.id.etRegEmailAddress)
        etRegPassword = findViewById(R.id.etRegPassword)
        etRegConfirmar = findViewById(R.id.etRegConfirmar)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseBD = FirebaseDatabase.getInstance().getReference("RegistrarseBD")
    }

    private fun initListeners() {
        viewRegCrearCuenta.setOnClickListener {
            crearCuenta()
        }
    }

    private fun crearCuenta() {
        val pass1 = etRegPassword.text.toString()
        val pass2 = etRegConfirmar.text.toString()
        val usuario = etRegUsuario.text.toString()
        val correo = etRegEmailAddress.text.toString()
        val puntos = 0
        val tickets = 0
        if (pass1.equals(pass2)){

           if (usuario.isNotEmpty()){

               firebaseAuth.createUserWithEmailAndPassword(etRegEmailAddress.text.toString(), etRegPassword.text.toString())
                   .addOnCompleteListener(this) { task ->
                       if (task.isSuccessful){
                           uploadData(correo, usuario, puntos, tickets)
                           Toast.makeText(baseContext,"Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                           navigateToMain()
                       }
                       else
                       {
                           Toast.makeText(baseContext,"Algo salio mal al crear la cuenta: " + task.exception, Toast.LENGTH_SHORT).show()
                       }
                   }

           }
           else
           {
               Toast.makeText(baseContext,"el campo usuario esta vacio", Toast.LENGTH_SHORT).show()
           }

        }
        else
        {
            Toast.makeText(baseContext,"Dos contraseñas diferentes introducidas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData(correo: String, usuario: String, puntos: Int, tickets: Int) {
        val usID = firebaseAuth.uid!!
        val usModel = UsuarioModel(usID, correo, usuario, puntos, tickets)
        firebaseBD.child(usID).setValue(usModel)
            .addOnCompleteListener {
                Toast.makeText(baseContext,"admin: data uploaded", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {err ->
                Toast.makeText(baseContext,"admin: data not uploaded: ${err.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}