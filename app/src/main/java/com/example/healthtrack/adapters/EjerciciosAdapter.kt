package com.example.healthtrack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtrack.R
import com.example.healthtrack.models.EjercicioModel
import com.example.healthtrack.models.historialModel

class EjerciciosAdapter (private val ejList: List<EjercicioModel>): RecyclerView.Adapter<EjerciciosAdapter.ViewHolder>() {
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
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textItemEj: TextView = itemView.findViewById(R.id.textItemEj)
        val textItemEj2: TextView = itemView.findViewById(R.id.textItemEj2)
        val textItemEj3: TextView = itemView.findViewById(R.id.textItemEj3)
    }

    override fun getItemCount(): Int {
        return ejList.size
    }
}


