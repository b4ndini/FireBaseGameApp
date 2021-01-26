package com.example.digitalhousegameapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.digitalhousegameapp.R
import com.example.digitalhousegameapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bind : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)



        bind.tilEmailEdit.setOnClickListener{
            bind.btnLogin.isEnabled = true
        }

        bind.btnLogin.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        }

        bind.btnRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}