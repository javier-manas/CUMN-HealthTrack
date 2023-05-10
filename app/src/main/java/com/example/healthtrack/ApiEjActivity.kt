package com.example.healthtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import com.example.healthtrack.databinding.ActivityApiEjBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiEjBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()

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

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://exercises-by-api-ninjas.p.rapidapi.com/v1/exercises?name=Rower")
                .get()
                .addHeader("X-RapidAPI-Key", "3efc9d020bmshaff1b716cc80165p16d9c8jsnfd91652ce974")
                .addHeader("X-RapidAPI-Host", "exercises-by-api-ninjas.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body
                if (responseBody != null) {
                    val responseString = responseBody.string()
                    // Hacer algo con la respuesta
                    Log.i("javi", "$responseString")
                    val jsonArray = JSONArray(responseString)
                    val firstObject = jsonArray.getJSONObject(0)
                    val name = firstObject.getString("name")

                    runOnUiThread { binding.tvApiEjP.text = name}
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