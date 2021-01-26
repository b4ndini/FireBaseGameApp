package com.example.digitalhousegameapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.digitalhousegameapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private val splashTimeout: Long = 1500
    private lateinit var bind: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(bind.root)
        navigateToMainActivity()
    }

    private fun navigateToMainActivity() {
        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, splashTimeout)
    }
}