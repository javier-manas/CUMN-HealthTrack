package com.example.healthtrack.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtrack.R
import com.example.healthtrack.models.historialModel

class HistorialAdapter (private val histList: ArrayList<historialModel>) : RecyclerView.Adapter<HistorialAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.historial_list_item, parent, false)
        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemHist = histList[position]
        holder.textItemHistorial.text = currentItemHist.ejercicio
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textItemHistorial : TextView = itemView.findViewById(R.id.textItemHistorial)

    }

    override fun getItemCount(): Int {
        return histList.size
    }



}