package com.example.healthtrack

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.healthtrack.models.RecompensasModel
import com.example.healthtrack.models.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.initialize
import com.google.firebase.ktx.options

class MainActivity : AppCompatActivity() {



    private lateinit var firebaseAuth: FirebaseAuth
    //private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    private lateinit var viewIniciarSesion: CardView
    private lateinit var viewRegistrarse: CardView
    private lateinit var viewOlvideMiContraseña: CardView
    private lateinit var etEmailAddress: TextView
    private lateinit var etPassword: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()



    }

    private fun initComponents() {
        viewIniciarSesion = findViewById(R.id.viewIniciarSesion)
        viewRegistrarse = findViewById(R.id.viewRegistrarse)
        viewOlvideMiContraseña = findViewById(R.id.viewOlvideMiContraseña)
        etEmailAddress = findViewById(R.id.etEmailAddress)
        etPassword = findViewById(R.id.etPassword)
        firebaseAuth = FirebaseAuth.getInstance()
        val ivMain = findViewById<ImageView>(R.id.ivMain)
        ivMain.setImageResource(R.mipmap.ic_main)
    }


    private fun initListeners() {
        viewIniciarSesion.setOnClickListener {
            signIn(etEmailAddress.text.toString(),etPassword.text.toString())
        }

        viewRegistrarse.setOnClickListener {
            navigateToRegistrarse()
        }

        viewOlvideMiContraseña.setOnClickListener {
            signIn("b@gmail.com","123123")
        }

    }

    private fun navigateToMenu() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToTuto() {
        val intent = Intent(this, TutoActivity::class.java)
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

                    val database = FirebaseDatabase.getInstance()
                    val myRef = database.getReference("RegistrarseBD/" + firebaseAuth.uid!!)

                    myRef.addValueEventListener(object : ValueEventListener {
                        @SuppressLint("SetTextI18n")
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val usProfile = snapshot.getValue(UsuarioModel::class.java)
                            val puntos = usProfile?.Puntos!!
                            if (puntos > 0){
                                navigateToMenu()
                            }else{
                                navigateToTuto()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(baseContext, "cancel", Toast.LENGTH_SHORT).show()
                        }
                    })

                }
                else
                {
                    Toast.makeText(baseContext,"Usuario o contraseña no correcto", Toast.LENGTH_SHORT).show()
                }
            }
    }


}