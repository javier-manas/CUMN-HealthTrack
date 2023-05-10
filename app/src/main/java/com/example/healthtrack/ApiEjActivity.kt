package com.example.healthtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthtrack.adapters.EjerciciosAdapter
import com.example.healthtrack.adapters.HistorialAdapter
import com.example.healthtrack.databinding.ActivityApiEjBinding
import com.example.healthtrack.models.EjercicioModel
import com.example.healthtrack.models.RecompensasModel
import com.example.healthtrack.models.UsuarioModel
import com.example.healthtrack.models.historialModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiEjActivity : AppCompatActivity() {



    private lateinit var binding: ActivityApiEjBinding
    private lateinit var rvApiEj: RecyclerView
    private lateinit var ejList: ArrayList<EjercicioModel>
    private lateinit var textCargandoEj: TextView


    private var Salida: String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiEjBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponents()
        initUI()

    }

    private fun initComponents() {

        rvApiEj = findViewById(R.id.rvApiEj)
        rvApiEj.layoutManager = LinearLayoutManager(this)
        rvApiEj.setHasFixedSize(true)

        ejList = arrayListOf<EjercicioModel>()
        textCargandoEj = findViewById(R.id.textCargandoEj)
        rvApiEj.visibility = View.GONE
        textCargandoEj.visibility = View.VISIBLE
        getExercises()
    }

    private fun initUI() {

        binding.SearchEj.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun construirRV() {


        val gson = Gson()
        val ejercicios = gson.fromJson(Salida, Array<EjercicioModel>::class.java)

        val listaEjercicios = mutableListOf<EjercicioModel>()

        for (ejercicio in ejercicios) {
            listaEjercicios.add(EjercicioModel(
                ejercicio.name,
                ejercicio.bodyPart,
                ejercicio.target,
                ejercicio.gifUrl
            ))
        }

        val adaptador = EjerciciosAdapter(listaEjercicios)
        rvApiEj.adapter = adaptador
        rvApiEj.visibility = View.VISIBLE
        textCargandoEj.visibility = View.GONE

        Log.i("javi","$Salida")
    }




    private fun searchByName(query: String) {
        getExercises(query)
    }

    private fun getExercises() {
        var responseString = "null"
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://exercisedb.p.rapidapi.com/exercises")
                .get()
                .addHeader("X-RapidAPI-Key", "3efc9d020bmshaff1b716cc80165p16d9c8jsnfd91652ce974")
                .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body
                if (responseBody != null) {
                    responseString = responseBody.string()
                    // Hacer algo con la respuesta
                    Log.i("javi", "getexercises $responseString")
                    Salida = responseString
                    runOnUiThread{construirRV()}
                } else {
                    // Manejar el caso en el que la respuesta no tenga cuerpo
                    Log.i("javi", "error de mierda de  lo de nulo")
                }
            } else {
                Log.i("javi", "falla la peticion")
            }
        }

    }

    private fun getExercises(query: String) {
        var responseString = "null"
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://exercisedb.p.rapidapi.com/exercises/name/$query")
                .get()
                .addHeader("X-RapidAPI-Key", "3efc9d020bmshaff1b716cc80165p16d9c8jsnfd91652ce974")
                .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body
                if (responseBody != null) {
                    responseString = responseBody.string()
                    // Hacer algo con la respuesta
                    Log.i("javi", "getexercises $responseString")
                    Salida = responseString
                    runOnUiThread{construirRV()}
                } else {
                    // Manejar el caso en el que la respuesta no tenga cuerpo
                    Log.i("javi", "error de mierda de  lo de nulo")
                }
            } else {
                Log.i("javi", "falla la peticion")
            }
        }

    }

}