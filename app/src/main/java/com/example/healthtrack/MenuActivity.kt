package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.healthtrack.models.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MenuActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseBD: DatabaseReference

    private lateinit var View1opcion: CardView
    private lateinit var View2opcion: CardView
    private lateinit var View3opcion: CardView
    private lateinit var View4opcion: CardView

    private lateinit var textUsuarioMenu: TextView
    private lateinit var textPuntosMenu: TextView
    private lateinit var textTicketsMenu: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initComponents()
        initListeners()
        getData()
        

    }

    private fun getData() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("RegistrarseBD/" + firebaseAuth.uid!! )
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(UsuarioModel::class.java)
                textUsuarioMenu.text = "User: " +userProfile?.usuario.toString()
                textPuntosMenu.text = "Puntos: " +userProfile?.Puntos.toString()
                textTicketsMenu.text = "Tickets: " + userProfile?.Tickets.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"cancel", Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun onBackPressed() {
        return
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_cerrarSesion -> {
                Toast.makeText(baseContext,"se ha cerrado sesion", Toast.LENGTH_SHORT).show()
                cerrarSesion()
            }
            R.id.item_ajustes -> {
                Toast.makeText(baseContext,"esta funcion aun no se ha implementado", Toast.LENGTH_SHORT).show()
            }
            R.id.item_clasificacion -> {
                Toast.makeText(baseContext,"esta funcion aun no se ha implementado", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initComponents() {
        View1opcion = findViewById(R.id.View1opcion)
        View2opcion = findViewById(R.id.View2opcion)
        View3opcion = findViewById(R.id.View3opcion)
        View4opcion = findViewById(R.id.View4opcion)

        textUsuarioMenu = findViewById(R.id.textUsuarioMenu)
        textPuntosMenu = findViewById(R.id.textPuntosMenu)
        textTicketsMenu = findViewById(R.id.textTicketsMenu)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseBD = FirebaseDatabase.getInstance().getReference("RegistrarseBD")
    }

    private fun initListeners() {
        View1opcion.setOnClickListener {
            navigateTo1opcion()
        }

        View2opcion.setOnClickListener {
            navigateTo2opcion()
        }

        View3opcion.setOnClickListener {
            navigateTo3opcion()
        }

        View4opcion.setOnClickListener {
            navigateTo4opcion()
        }

    }

    private fun navigateTo1opcion() {
        val intent = Intent(this, ImcActivity::class.java)
        startActivity(intent)
    }

    private fun navigateTo2opcion() {
        val intent = Intent(this, EjercicioActivity::class.java)
        startActivity(intent)
    }

    private fun navigateTo3opcion() {
        val intent = Intent(this, HistorialActivity::class.java)
        startActivity(intent)
    }

    private fun navigateTo4opcion() {
        val intent = Intent(this, RecompensasActivity::class.java)
        startActivity(intent)
    }

    private fun cerrarSesion() {
        firebaseAuth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}