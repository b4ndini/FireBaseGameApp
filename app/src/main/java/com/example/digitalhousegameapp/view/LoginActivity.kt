package com.example.digitalhousegameapp.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.digitalhousegameapp.databinding.ActivityLoginBinding
import com.example.digitalhousegameapp.utils.isEmailValid
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {


    private lateinit var bind: ActivityLoginBinding

    private val firebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bind.root)


        if (firebaseAuth.currentUser != null)
            startActivity(Intent(this, HomeActivity::class.java))

        SignUp()



        bind.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (firebaseAuth.currentUser != null)
            finish()
    }


    private fun SignUp() {

        var email: String
        var password: String

        bind.tilEmailEdit.addTextChangedListener(loginWatcher)
        bind.tilPasswordEdit.addTextChangedListener(loginWatcher)

        bind.btnLogin.setOnClickListener {


            email = bind.tilEmailEdit.text.toString()
            password = bind.tilPasswordEdit.text.toString()


            // if(email.isEmailValid()) {
            //  if (password.equals(confirmPass)) {

            firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            )
                .addOnSuccessListener {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login efetuado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // val user = firebaseAuth.currentUser

                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    when (it) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            bind.tilPassword.error = "Senha inválida"
                        }
                        is FirebaseAuthInvalidUserException -> {
                            bind.tilEmail.error = "Usuário inválido"
                        }
                        is FirebaseTooManyRequestsException -> {
                            Toast.makeText(this@LoginActivity, "Conta temporariamente bloqueada devido a várias tentativas de login suspeitas", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> {
                            Toast.makeText(this@LoginActivity, "Erro interno", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                }
            /*  } else {

                  bind.tilRepeatPassword.error = "As senhas não coincidem"


              }*/

            /* }else{
                 bind.tilEmail.error = "E-mail inválido."
             }*/
        }


    }


    private val loginWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            bind.tilPassword.isErrorEnabled = false
            bind.tilEmail.isErrorEnabled = false

            val emailInput: String = bind.tilEmailEdit.text.toString().trim()
            val passwordInput: String = bind.tilPasswordEdit.text.toString().trim()


            enableRegister(emailInput, passwordInput)

        }

        override fun afterTextChanged(s: Editable) {

        }
    }


    private fun enableRegister(email: String, password: String) {
        bind.btnLogin.isEnabled =
            !email.isNullOrBlank() && email.isEmailValid() && !password.isNullOrBlank() && password.length >= 6
    }


}