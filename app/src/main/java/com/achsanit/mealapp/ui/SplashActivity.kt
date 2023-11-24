package com.achsanit.mealapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.asLiveData
import com.achsanit.mealapp.data.local.DataStoreManager
import com.achsanit.mealapp.databinding.ActivitySplashBinding
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val dataStoreManager: DataStoreManager by inject()
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed({
            dataStoreManager.isLoggedIn().asLiveData().observe(this) {
                val intent =
                    Intent(this, if (it) MainActivity::class.java else AuthActivity::class.java )
                startActivity(intent)
                finish()
            }
        }, 2000L)
    }
}