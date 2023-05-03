package com.example.healthtrack

import android.annotation.SuppressLint

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.healthtrack.models.RecompensasModel
import com.example.healthtrack.models.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RecompensasActivity : AppCompatActivity() {

    private lateinit var ViewColeccion: CardView
    private lateinit var ViewCanjear: CardView
    private lateinit var tvPuntos: TextView
    private var Tema2: Boolean = false

    private lateinit var firebaseBD: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recompensas)
        val Tickets: Int = intent.extras?.getInt(MenuActivity.Tickets_key) ?: -2
        val Puntos: Int = intent.extras?.getInt(MenuActivity.Puntos_key) ?: -2
        val Correo: String = intent.extras?.getString(MenuActivity.Correo_key) ?: "-2"
        val Usuario: String = intent.extras?.getString(MenuActivity.Usuario_key) ?: "-2"
        val UsId: String = intent.extras?.getString(MenuActivity.UsId_key) ?: "-2"
        initComponents()
        initUI(Tickets)
        initListeners(Usuario, UsId, Correo, Puntos, Tickets)

    }

    private fun initComponents() {
        ViewColeccion = findViewById(R.id.ViewColeccion)
        ViewCanjear = findViewById(R.id.ViewCanjear1)
        tvPuntos = findViewById(R.id.tvPuntos)
        firebaseAuth = FirebaseAuth.getInstance()
        tieneElTema()
    }

    private fun initListeners(
        Usuario: String,
        UsId: String,
        Correo: String,
        Puntos: Int,
        Tickets: Int
    ) {
        ViewColeccion.setOnClickListener {
            cambiarColorMedianoche()

        }

        ViewCanjear.setOnClickListener {
            if (Tema2) {
                Log.i("javi", "tiene el tema")
                cambiarColorAmanecer()
            } else {
                if (Tickets >= 2) {
                    val TicketsMod = Tickets - 2
                    updateData(Usuario, UsId, Correo, Puntos, TicketsMod)
                    initUI(TicketsMod)
                } else {
                    Toast.makeText(baseContext, "no tienes tickets suficientes", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        }

    }

    private fun tieneElTema() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("RecompensasBD/" + firebaseAuth.uid!!)


        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val reProfile = snapshot.getValue(RecompensasModel::class.java)
                Tema2 = reProfile?.Tema2!!
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "cancel", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun updateData(
        Usuario: String,
        UsId: String,
        Correo: String,
        Puntos: Int,
        Tickets: Int
    ) {
        firebaseBD = FirebaseDatabase.getInstance().getReference("RegistrarseBD")

        val usModel = UsuarioModel(UsId, Correo, Usuario, Puntos, Tickets)
        firebaseBD.child(firebaseAuth.uid!!).setValue(usModel).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                cambiarColorAmanecer()
                firebaseBD = FirebaseDatabase.getInstance().getReference("RecompensasBD/")
                val reModel = RecompensasModel(true, true, false, false, false)
                firebaseBD.child(firebaseAuth.uid!!).setValue(reModel)
            } else {
                Toast.makeText(baseContext, "error al canjear", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun cambiarColorMedianoche() {

        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }

    private fun cambiarColorAmanecer() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()

    }


    private fun initUI(valorPuntos: Int) {
        tvPuntos.text = "Tus Tickets: $valorPuntos"
    }
}