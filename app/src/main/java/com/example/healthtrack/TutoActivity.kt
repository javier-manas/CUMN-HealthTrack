package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TutoActivity : AppCompatActivity() {

    private lateinit var tvTuto1: TextView
    private lateinit var tvTuto2: TextView
    private lateinit var tvTuto3: TextView
    private lateinit var tvTuto4: TextView
    private lateinit var tvTuto5: TextView
    private lateinit var btnTuto: Button
    var cont = 0
    private lateinit var ivTuto1: ImageView
    private lateinit var ivTuto2: ImageView
    private lateinit var ivTuto3: ImageView
    private lateinit var ivTuto4: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tuto)
        initComponents()
        initListeners()
    }



    private fun initListeners() {
        btnTuto.setOnClickListener {
            siguiente()
        }


    }

    private fun siguiente() {
        cont += 1
        when(cont){
            1 -> {
                ivTuto1.visibility = View.GONE
                tvTuto1.visibility = View.GONE

                ivTuto2.visibility = View.VISIBLE
                tvTuto2.visibility = View.VISIBLE
            }
            2 -> {
                ivTuto2.visibility = View.GONE
                tvTuto2.visibility = View.GONE

                ivTuto3.visibility = View.VISIBLE
                tvTuto3.visibility = View.VISIBLE
            }
            3 -> {
                ivTuto3.visibility = View.GONE
                tvTuto3.visibility = View.GONE

                ivTuto4.visibility = View.VISIBLE
                tvTuto4.visibility = View.VISIBLE
            }
            4 -> {
                ivTuto4.visibility = View.GONE
                tvTuto4.visibility = View.GONE

                tvTuto5.visibility = View.VISIBLE
            }
            5 -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initComponents() {

        tvTuto1 = findViewById(R.id.tvTuto1)
        tvTuto2 = findViewById(R.id.tvTuto2)
        tvTuto3 = findViewById(R.id.tvTuto3)
        tvTuto4 = findViewById(R.id.tvTuto4)
        tvTuto5 = findViewById(R.id.tvTuto5)
        btnTuto = findViewById(R.id.btnTuto)

        ivTuto1 = findViewById<ImageView>(R.id.ivTuto1)
        ivTuto2 = findViewById<ImageView>(R.id.ivTuto2)
        ivTuto3 = findViewById<ImageView>(R.id.ivTuto3)
        ivTuto4 = findViewById<ImageView>(R.id.ivTuto4)

        ivTuto1.setImageResource(R.mipmap.ic_tutouno_foreground)
        ivTuto2.setImageResource(R.mipmap.ic_tutodos_foreground)
        ivTuto3.setImageResource(R.mipmap.ic_tutotres_foreground)
        ivTuto4.setImageResource(R.mipmap.ic_tutocuatro_foreground)

        ivTuto1.visibility = View.VISIBLE
        tvTuto1.visibility = View.VISIBLE
    }
}