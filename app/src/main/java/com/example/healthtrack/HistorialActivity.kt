package com.example.healthtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtrack.adapters.HistorialAdapter
import com.example.healthtrack.models.historialModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistorialActivity : AppCompatActivity() {

    private lateinit var recyclerHistorial: RecyclerView
    private lateinit var textCargandoHistorial: TextView
    private lateinit var textPuntosHistorial: TextView
    private lateinit var textUsuarioHistorial: TextView
    private lateinit var histList: ArrayList<historialModel>
    private lateinit var firebaseBD: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)
        initComponents()
        getUsuarioData()
        getHistorialData()

    }

    private fun getHistorialData() {
        recyclerHistorial.visibility = View.GONE
        textCargandoHistorial.visibility = View.VISIBLE

        firebaseBD.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                histList.clear()
                if (snapshot.exists()){
                    for (histSnap in snapshot.children){
                        val histData = histSnap.getValue(historialModel::class.java)
                        histList.add(histData!!)
                    }
                    val hAdapter = HistorialAdapter(histList)
                    recyclerHistorial.adapter = hAdapter

                    recyclerHistorial.visibility = View.VISIBLE
                    textCargandoHistorial.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getUsuarioData() {

    }

    private fun initComponents() {
        recyclerHistorial = findViewById(R.id.recyclerHistorial)
        recyclerHistorial.layoutManager = LinearLayoutManager(this)
        recyclerHistorial.setHasFixedSize(true)

        histList = arrayListOf<historialModel>()

        textCargandoHistorial = findViewById(R.id.textCargandoHistorial)
        textPuntosHistorial = findViewById(R.id.textPuntosHistorial)
        textUsuarioHistorial = findViewById(R.id.textUsuarioHistorial)
        firebaseBD = FirebaseDatabase.getInstance().getReference("HistorialBD")
    }




}