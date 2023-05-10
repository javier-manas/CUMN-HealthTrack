package com.example.healthtrack

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val textItemEj: TextView = itemView.findViewById(R.id.textItemEj)
    val textItemEj2: TextView = itemView.findViewById(R.id.textItemEj2)
    val textItemEj3: TextView = itemView.findViewById(R.id.textItemEj3)
    val btnAction: Button = itemView.findViewById(R.id.btnAction)

    init {
        btnAction.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        // Aquí puedes realizar la acción que deseas realizar al hacer clic en el botón
        Log.i("javi","viewholder")
    }
}