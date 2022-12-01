package com.example.food01android.activity.LogIn

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.food01android.FoodApi
import com.example.food01android.R
import com.example.food01android.activity.MainActivity
import com.example.food01android.databinding.ActivityLoginBinding
import com.example.food01android.databinding.ActivityVegetarianBinding
import com.example.food01android.model.Home.Home
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VegetarianActivity : AppCompatActivity() {
    lateinit var binding: ActivityVegetarianBinding
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVegetarianBinding.inflate(layoutInflater)
        val view = binding.root
        sharedPreferences = this.getSharedPreferences("dataLogin", AppCompatActivity.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        binding.btnNo.setOnClickListener {
            setVegan(0)
            editor.putInt("vegan", 0)
            editor.commit()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnYes.setOnClickListener {
            setVegan(1)
            editor.putInt("vegan", 1)
            editor.commit()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(view)
    }

    fun setVegan(vegan: Int) {
        val token: String = "Bearer" + sharedPreferences?.getString("token", "").toString()
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.setVegan(token, vegan).enqueue(object : Callback<Home> {
            override fun onResponse(call: Call<Home>, response: Response<Home>) {


            }

            override fun onFailure(call: Call<Home>, t: Throwable) {
                Log.d("Home", "error getdata " + t)
            }
        })
    }
}

