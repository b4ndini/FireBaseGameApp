package com.example.digitalhousegameapp.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.digitalhousegameapp.databinding.ActivityGameDetailBinding
import com.example.digitalhousegameapp.model.Game
import com.google.firebase.firestore.FirebaseFirestore


class GameDetailActivity : AppCompatActivity() {

    private lateinit var bind: ActivityGameDetailBinding
    private val fireDB by lazy{
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)




    }

    override fun onStart(){
        super.onStart()

        var game = intent.getStringExtra("game_id") ?: "0"

        val docRef = fireDB.collection("games").document(game)


                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.i("funcionou", "DocumentSnapshot data: ${document.data}")
                                var game = document.toObject(Game::class.java)
                                bind.tvGameTitle.text = game?.name ?: "Not found"
                                bind.tvGameTitleDescription.text = game?.name ?: "Not found"
                                bind.tvGameDescription.text = game?.description ?: "Not found"
                                bind.tvReleaseDate.text = "Lançamento: ${game?.date}" ?: "Unknown"
                                Glide.with(applicationContext).load(game?.image).into(bind.ivGameImage)


                            } else {
                                Log.i("funcionou", "No such document")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.i("funcionou", "get failed with exceção: ", exception)
                        }

    }





}