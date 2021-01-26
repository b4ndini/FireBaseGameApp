package com.example.digitalhousegameapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.digitalhousegameapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var bind : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnCreateAccount.setOnClickListener{
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }

        bind.tilEmailEdit.setOnClickListener{
            bind.btnCreateAccount.isEnabled = true
        }
    }

}