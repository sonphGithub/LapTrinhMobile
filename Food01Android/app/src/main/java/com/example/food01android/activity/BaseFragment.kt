package com.example.food01android.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment: Fragment() {


    var sharedPreferences: SharedPreferences? = null
    var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            activity?.getSharedPreferences("dataLogin", AppCompatActivity.MODE_PRIVATE)
        token = "Bearer" + sharedPreferences?.getString("token", "").toString()
        Log.e("tokenn", token)


    }

    fun hideKeyboard(){
        val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun showKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }




}