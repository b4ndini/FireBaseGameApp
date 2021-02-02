package com.example.digitalhousegameapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.digitalhousegameapp.databinding.ActivityRegisterGameBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterGameActivity : AppCompatActivity() {

    private lateinit var bind: ActivityRegisterGameBinding

    private val fireDB by lazy{
        Firebase.firestore
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterGameBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.tilGameNameEdit.setOnClickListener{bind.btnSaveGame.isEnabled = true}
        bind.btnSaveGame.setOnClickListener {
            val game = hashMapOf(
            "name" to bind.tilGameNameEdit.text.toString(),
            "date" to bind.tilCreateDateEdit.text.toString(),
            "description" to bind.tilDescriptionEdit.text.toString(),
                "image" to "https://kanto.legiaodosherois.com.br/w760-h398-gnw-cfill-q80/wp-content/uploads/2015/08/50c2877ed5f7e29d65362c359a7fd82a.jpg.jpeg"
            )
            fireDB.collection("games")
                .document()
                .set(game)
                .addOnSuccessListener { Toast.makeText(this@RegisterGameActivity, "Adicionado com sucesso", Toast.LENGTH_SHORT)
                    .show() }
                .addOnFailureListener { Toast.makeText(this@RegisterGameActivity, "ERRO ERRO ERRO", Toast.LENGTH_SHORT)
                    .show() }
        }

    }
}