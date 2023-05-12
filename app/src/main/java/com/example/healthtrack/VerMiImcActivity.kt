package com.example.healthtrack

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.healthtrack.ActualizarMiImcActivity.Companion.IMC_KEY
import com.example.healthtrack.models.ImcModel
import com.example.healthtrack.models.UsuarioModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerMiImcActivity : AppCompatActivity() {

    private lateinit var tvResult:TextView
    private lateinit var tvIMC:TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnActualizar: Button
    private var imc: Double = 0.0
    private var resultado: Double = 0.0
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_mi_imc)
        val result:Double = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        resultado = result
        getData()
        initComponents()
        initListeners()
    }

    override fun onBackPressed() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initListeners() {
        btnActualizar.setOnClickListener {
            navigateToActualizarMiImc()
        }
    }

    private fun getData() {
        firebaseAuth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("ImcBD/" + firebaseAuth.uid!! )
        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val ImcProfile = snapshot.getValue(ImcModel::class.java)

                imc = ImcProfile?.Imc!!
                initUI()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext,"cancel", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initUI() {
        var result = 0.0
        if (imc > 1.0){
            result = imc
        }else{
            result = resultado
        }

        tvIMC.text = result.toString()
        when(result){
            in 0.00..0.09 -> { //Auun no has introducido tu imc
                tvResult.text = getString(R.string.title_no_peso)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.colorTexto))
                tvDescription.text = getString(R.string.description_no_peso)
            }
            in 0.10..18.50 -> { //Bajo peso
                tvResult.text = getString(R.string.title_bajo_peso)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_bajo))
                tvDescription.text = getString(R.string.description_bajo_peso)
            }
            in 18.51..24.99 -> { //Peso normal
                tvResult.text = getString(R.string.title_peso_normal)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_normal))
                tvDescription.text = getString(R.string.description_peso_normal)
            }
            in 25.00..29.99 -> { //Sobrepeso
                tvResult.text = getString(R.string.title_sobrepeso)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.peso_sobrepeso))
                tvDescription.text = getString(R.string.description_sobrepeso)
            }
            in 30.00..99.00 -> { //Obesidad
                tvResult.text = getString(R.string.title_obesidad)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
                tvDescription.text = getString(R.string.description_obesidad)
            }
            else -> {//error
                tvIMC.text = getString(R.string.error)
                tvResult.text = getString(R.string.error)
                tvResult.setTextColor(ContextCompat.getColor(this, R.color.obesidad))
                tvDescription.text = getString(R.string.error)
            }
        }
    }

    private fun initComponents() {
        tvIMC = findViewById(R.id.tvIMC)
        tvResult = findViewById(R.id.tvResult)
        tvDescription = findViewById(R.id.tvDescription)
        btnActualizar = findViewById(R.id.btnRecalculate)
    }

    private fun navigateToActualizarMiImc() {
        val intent = Intent(this, ActualizarMiImcActivity::class.java)
        startActivity(intent)
    }
}