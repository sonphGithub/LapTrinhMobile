package com.example.food01android.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.food01android.R
import com.example.food01android.databinding.ActivityMainBinding
import com.example.food01android.transparentStatusAndNavigation
import io.realm.Realm


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var fragmentHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        fragmentHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = fragmentHost.navController
        binding.bottomNavigation.setupWithNavController(navController)
        setContentView(binding.root)

        this.transparentStatusAndNavigation()
        Realm.init(this)


    }

    override fun onBackPressed() {
        super.onBackPressed()

    }


    fun hideBottomBar() {
        binding.bottomNavigation.isVisible = false
    }

    fun showBottomBar() {
        binding.bottomNavigation.isVisible = true
    }

}