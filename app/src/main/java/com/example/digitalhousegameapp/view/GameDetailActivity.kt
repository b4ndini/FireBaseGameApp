package com.example.digitalhousegameapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.digitalhousegameapp.databinding.ActivityGameDetailBinding

class GameDetailActivity : AppCompatActivity() {

    private lateinit var bind: ActivityGameDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)

    }
}