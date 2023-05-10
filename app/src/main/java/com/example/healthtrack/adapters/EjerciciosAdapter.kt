package com.example.healthtrack.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtrack.ImcActivity
import com.example.healthtrack.R
import com.example.healthtrack.models.EjercicioModel
import com.example.healthtrack.models.UsuarioModel
import com.example.healthtrack.models.historialModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EjerciciosAdapter (private val ejList: List<EjercicioModel> ): RecyclerView.Adapter<EjerciciosAdapter.ViewHolder>() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseBD: DatabaseReference

    private lateinit var Usuario: String
    private lateinit var Correo: String
    private lateinit var UsId: String
    private var Puntos: Int = -1
    private var Tickets: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.ejercicio_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemHist = ejList[position]
        holder.textItemEj.text = currentItemHist.name
        holder.textItemEj2.text = currentItemHist.bodyPart
        holder.textItemEj3.text = currentItemHist.target

        val  ejercicio = currentItemHist.name

        firebaseAuth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("RegistrarseBD/" + firebaseAuth.uid!! )
        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(UsuarioModel::class.java)

                Usuario = userProfile?.usuario.toString()
                Correo = userProfile?.correo.toString()
                UsId = userProfile?.usID.toString()
                Puntos = userProfile?.Puntos!!
                Tickets = userProfile?.Tickets!!

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        holder.btnAction.setOnClickListener {

            // Aquí puedes realizar la acción que deseas realizar al hacer clic en el botón

            firebaseBD = FirebaseDatabase.getInstance().getReference("HistorialBD")
            val ejID = firebaseBD.push().key!!
            val ejModel = historialModel(ejID, ejercicio)
            firebaseBD.child(firebaseAuth.uid!!).child(ejID).setValue(ejModel)
            aumentarPuntos()



        }
    }

    private fun aumentarPuntos(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("RegistrarseBD/" + firebaseAuth.uid!! )
        Tickets += 1
        Puntos += 1
        val usModel = UsuarioModel(UsId, Correo, Usuario, Puntos, Tickets)
        myRef.setValue(usModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnAction:Button = itemView.findViewById(R.id.btnAction)
        val textItemEj: TextView = itemView.findViewById(R.id.textItemEj)
        val textItemEj2: TextView = itemView.findViewById(R.id.textItemEj2)
        val textItemEj3: TextView = itemView.findViewById(R.id.textItemEj3)
    }

    override fun getItemCount(): Int {
        return ejList.size
    }
}


