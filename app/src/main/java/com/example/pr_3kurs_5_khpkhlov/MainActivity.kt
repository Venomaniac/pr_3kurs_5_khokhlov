package com.example.pr_3kurs_5_khpkhlov

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class MainActivity : ComponentActivity() {

    private val key = "5288ab04a2d4c745daee815dd6a6767e"
    private val baseUrl = "https://api.openweathermap.org/data/2.5/weather?q="

    private lateinit var city: EditText
    private lateinit var btnGet: Button
    private lateinit var tvCity: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvPressure: TextView
    private lateinit var tvWind: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city = findViewById(R.id.city)
        btnGet = findViewById(R.id.btnGet)
        tvCity = findViewById(R.id.tvCity)
        tvTemp = findViewById(R.id.tvTemp)
        tvDesc = findViewById(R.id.tvDesc)
        tvPressure = findViewById(R.id.tvPressure)
        tvWind = findViewById(R.id.tvWind)

        btnGet.setOnClickListener {
            if (city.text.toString().isEmpty()) {
                Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show()
            } else {
                getWeather()
            }
        }
    }
    private fun getWeather() {
        val url = baseUrl + city.text.toString() +
                "&appid=" + key + "&units=metric&lang=ru"

        val queue = Volley.newRequestQueue(this)

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("WEATHER", response)
                val weather = Gson().fromJson(response, WeatherResponse::class.java)

                Log.d("WEATHER", "Город: ${weather.name}, температура: ${weather.main.temp}")
                tvCity.text = "Город: ${weather.name}"
                tvTemp.text = "Температура: ${weather.main.temp} °C"
                tvDesc.text = "Погода: ${weather.weather[0].description}"
                tvPressure.text = "Давление: ${weather.main.pressure} гПа"
                tvWind.text = "Скорость ветра: ${weather.wind.speed} м/с"
            },
            { error ->
                Log.d("WEATHER", "Ошибка: ${error.message}")
                Toast.makeText(this, "Город не найден или нет сети", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
    }
}
