package com.example.digitalhousegameapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.digitalhousegameapp.databinding.ActivityRegisterGameBinding

class RegisterGameActivity : AppCompatActivity() {

    private lateinit var bind: ActivityRegisterGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

    }
}