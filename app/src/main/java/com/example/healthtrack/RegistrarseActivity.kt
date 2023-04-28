package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

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
    }

    private fun initListeners() {
        viewRegCrearCuenta.setOnClickListener {
            crearCuenta()
        }



    }

    private fun crearCuenta() {
        var pass1 = etRegPassword.text.toString()
        var pass2 = etRegConfirmar.text.toString()
        if (pass1.equals(pass2)){

            firebaseAuth.createUserWithEmailAndPassword(etRegEmailAddress.text.toString(), etRegPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful){
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
            Toast.makeText(baseContext,"Dos contraseñas diferentes introducidas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}