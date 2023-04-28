package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var View1opcion: CardView
    private lateinit var View2opcion: CardView
    private lateinit var View3opcion: CardView
    private lateinit var View4opcion: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initComponents()
        initListeners()
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initComponents() {
        View1opcion = findViewById(R.id.View1opcion)
        View2opcion = findViewById(R.id.View2opcion)
        View3opcion = findViewById(R.id.View3opcion)
        View4opcion = findViewById(R.id.View4opcion)
        firebaseAuth = FirebaseAuth.getInstance()
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