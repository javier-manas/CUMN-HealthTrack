package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.initialize
import com.google.firebase.ktx.options

class MainActivity : AppCompatActivity() {

    private var usuario: String = "True"

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var viewIniciarSesion: CardView
    private lateinit var viewRegistrarse: CardView
    private lateinit var etEmailAddress: TextView
    private lateinit var etPassword: TextView


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
        viewRegistrarse = findViewById(R.id.viewRegistrarse)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        firebaseAuth = FirebaseAuth.getInstance()
    }


    private fun initListeners() {
        viewIniciarSesion.setOnClickListener {
            signIn(etEmailAddress.text.toString(),etPassword.text.toString())
        }

        viewRegistrarse.setOnClickListener {
            navigateToRegistrarse()
        }

    }

    private fun navigateToMenu(usuario: String) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(USARIO_KEY, usuario)
        startActivity(intent)
    }

    private fun navigateToRegistrarse() {
        val intent = Intent(this, RegistrarseActivity::class.java)
        startActivity(intent)
    }

    private fun signIn(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener (this)  { task ->
                if (task.isSuccessful){
                    val user = firebaseAuth.currentUser
                    Toast.makeText(baseContext,"Usuario y contraseña correctos", Toast.LENGTH_SHORT).show()
                    navigateToMenu(usuario)
                }
                else
                {
                    Toast.makeText(baseContext,"Usuario o contraseña no correcto", Toast.LENGTH_SHORT).show()
                }
            }
    }


}