package com.example.sehatmentalku.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.sehatmentalku.R
import com.example.sehatmentalku.databinding.ActivitySplashScreenBinding
import com.example.sehatmentalku.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val loading = findViewById<ProgressBar>(R.id.loading)

        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(4000)
                    loading.visibility= View.VISIBLE
                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}