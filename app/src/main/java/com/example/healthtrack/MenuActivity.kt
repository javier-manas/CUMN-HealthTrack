package com.example.healthtrack

import android.annotation.SuppressLint
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

    private lateinit var Usuario: String
    private lateinit var Correo: String
    private lateinit var UsId: String
    private var Puntos: Int = -1
    private var Tickets: Int = -1

    companion object{
        const val Correo_key = "Correo_valor"
        const val UsId_key = "UsId_valor"
        const val Usuario_key = "Usuario_valor"
        const val Puntos_key = "Puntos_valor"
        const val Tickets_key = "Tickets_valor"
    }


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
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(UsuarioModel::class.java)

                 Usuario = userProfile?.usuario.toString()
                 Correo = userProfile?.correo.toString()
                 UsId = userProfile?.usID.toString()
                 Puntos = userProfile?.Puntos!!
                 Tickets = userProfile?.Tickets!!

                textTicketsMenu.text = "Tickets: $Tickets"
                textPuntosMenu.text = "Puntos: $Puntos"
                textUsuarioMenu.text = "User: $Usuario"

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
        intent.putExtra("Usuario_valor", Usuario)
        intent.putExtra("Puntos_valor", Puntos)
        intent.putExtra("Tickets_valor", Tickets)
        intent.putExtra("Correo_valor", Correo)
        intent.putExtra("UsId_valor", UsId)
        startActivity(intent)
    }

    private fun navigateTo2opcion() {
        val intent = Intent(this, EjercicioActivity::class.java)
        intent.putExtra("Usuario_valor", Usuario)
        intent.putExtra("Puntos_valor", Puntos)
        intent.putExtra("Tickets_valor", Tickets)
        intent.putExtra("Correo_valor", Correo)
        intent.putExtra("UsId_valor", UsId)
        startActivity(intent)
    }

    private fun navigateTo3opcion() {
        val intent = Intent(this, HistorialActivity::class.java)
        intent.putExtra("Usuario_valor", Usuario)
        intent.putExtra("Puntos_valor", Puntos)
        intent.putExtra("Tickets_valor", Tickets)
        startActivity(intent)
    }

    private fun navigateTo4opcion() {
        val intent = Intent(this, RecompensasActivity::class.java)
        intent.putExtra("Usuario_valor", Usuario)
        intent.putExtra("Puntos_valor", Puntos)
        intent.putExtra("Tickets_valor", Tickets)
        intent.putExtra("Correo_valor", Correo)
        intent.putExtra("UsId_valor", UsId)
        startActivity(intent)
    }

    private fun cerrarSesion() {
        firebaseAuth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}