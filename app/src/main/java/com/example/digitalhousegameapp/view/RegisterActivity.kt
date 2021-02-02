package com.example.digitalhousegameapp.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalhousegameapp.databinding.ActivityRegisterBinding
import com.example.digitalhousegameapp.utils.isEmailValid
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : AppCompatActivity() {

    private lateinit var bind: ActivityRegisterBinding

    private val firebaseAuth by lazy {
        Firebase.auth
    }


    override fun onStart(){
        super.onStart()
        if (firebaseAuth.currentUser != null)
            finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)
        SignUp()

        /*  bind.btnCreateAccount.setOnClickListener{
              startActivity(Intent(this, RegisterGameActivity::class.java))
          }

          bind.tilEmailEdit.setOnClickListener{
              bind.btnCreateAccount.isEnabled = true
          }*/
    }



    private fun SignUp() {
        var name: String?
        var email: String
        var password: String
        var confirmPass: String?


        /*  if(name.is != (null) && email != null&& password != null && confirmPass != null){
              bind.btnCreateAccount.isEnabled
          }*/

        /*   bind.tilEmailEdit.setOnClickListener{
               bind.btnCreateAccount.isEnabled = true
           }*/


        bind.tilNameEdit.addTextChangedListener(registerWatcher)
        bind.tilEmailEdit.addTextChangedListener(registerWatcher)
        bind.tilPasswordEdit.addTextChangedListener(registerWatcher)
        bind.tilRepeatPasswordEdit.addTextChangedListener(registerWatcher)




        bind.btnCreateAccount.setOnClickListener {
            //val user: User
            //bind.tilRepeatPassword.error = null
            bind.tilRepeatPassword.isErrorEnabled = false


            name = bind.tilNameEdit.text.toString()
            email = bind.tilEmailEdit.text.toString()
            password = bind.tilPasswordEdit.text.toString()
            confirmPass = bind.tilRepeatPasswordEdit.text.toString()

            if(email.isEmailValid()) {
                if (password.equals(confirmPass)) {

                    firebaseAuth.createUserWithEmailAndPassword(
                        email,
                        password
                    )
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@RegisterActivity,
                                "USUARIO CADASTRADO COM SUCESSO",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                            intent.putExtra("username", name)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this@RegisterActivity,
                                "ERRO ERRO ERRO ERRO ERRO",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {

                    bind.tilRepeatPassword.error = "As senhas não coincidem"


                }

            }else{
                bind.tilEmail.error = "E-mail inválido."
            }
        }
    }

    private val registerWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            bind.tilRepeatPassword.isErrorEnabled = false
            bind.tilEmail.isErrorEnabled = false
            val nameInput: String = bind.tilNameEdit.text.toString().trim()
            val emailInput: String = bind.tilEmailEdit.text.toString().trim()
            val passwordInput: String = bind.tilPasswordEdit.text.toString().trim()
            val confirmPassInput: String = bind.tilRepeatPasswordEdit.text.toString().trim()

            enableRegister(nameInput, emailInput, passwordInput, confirmPassInput)
            /* if (!emailInput.isNullOrBlank()) {
                if (!emailInput.isEmailValid()) {
                    bind.tilEmail.error = "E-mail inválido."
                } else {
                    bind.tilEmail.isErrorEnabled = false
                }
            } else {
                bind.tilEmail.isErrorEnabled = false
                enableRegister(nameInput, emailInput, passwordInput, confirmPassInput)
            }
        }*/
        }

        override fun afterTextChanged(s: Editable) {

        }
    }




    private fun enableRegister(name: String, email: String, password: String, confirmPass: String) {
        bind.btnCreateAccount.isEnabled = !name.isNullOrBlank() && !email.isNullOrBlank() && !password.isNullOrBlank() && !confirmPass.isNullOrBlank()
    }
}