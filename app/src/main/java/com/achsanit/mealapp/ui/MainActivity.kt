package com.achsanit.mealapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.achsanit.mealapp.R
import com.achsanit.mealapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavBar()
    }

    private fun setUpNavBar() {
        val navBar = binding.navigationBar
        val navController = findNavController(R.id.nav_host_main_fragment)

        navBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.nav_home -> setVisibilityBottomNav(true)
                R.id.nav_user -> setVisibilityBottomNav(true)
                else -> setVisibilityBottomNav(false)
            }
        }
    }

    private fun setVisibilityBottomNav(isVisible: Boolean) {
        with(binding) {
            if (isVisible) {
                navigationBar.visibility = View.VISIBLE
            } else {
                navigationBar.visibility = View.GONE
            }
        }
    }
}