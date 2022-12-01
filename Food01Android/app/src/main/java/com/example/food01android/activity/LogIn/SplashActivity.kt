package com.example.food01android.activity.LogIn

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.food01android.R
import com.example.food01android.activity.MainActivity


class SplashActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE)


    val token: String =  sharedPreferences.getString("token","").toString()
        if (token == ""){
        val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }else{
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            },1500)

        }
    }


}