package com.example.digitalhousegameapp.view


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalhousegameapp.databinding.ActivityHomeBinding
import com.example.digitalhousegameapp.model.Game
import com.example.digitalhousegameapp.view.adapter.HomeAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity(), OnClickListenerInterface {


    private val db:FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionReference: CollectionReference = db.collection("games")


    private lateinit var bind: ActivityHomeBinding
    var gameAdapter: HomeAdapter? = null

    private val firebaseAuth by lazy {
        Firebase.auth
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        val user = firebaseAuth.currentUser


        /*var name = intent.getStringExtra("username")
        bind.tvUserName.text = name*/


        bind.fabAddGame.setOnClickListener{
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }



        setupRecyclerView()
    }



    private fun setupRecyclerView() {


       val query: Query = collectionReference



        val options: FirestoreRecyclerOptions<Game> = FirestoreRecyclerOptions.Builder<Game>()
            .setQuery(query, Game::class.java)
            .build()


        gameAdapter = HomeAdapter(options, this)
        bind.rvGamesList.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 2)
            adapter = gameAdapter
        }

    }

    override fun onStart() {
        super.onStart()
        var name = intent.getStringExtra("user_name")
        gameAdapter?.startListening()


    }

    override fun onStop(){
        super.onStop()
        gameAdapter?.stopListening()
    }

    override fun onItemClicked(documentSnapshot: DocumentSnapshot, position: Int) {

        var id : String = documentSnapshot.id
        val intent = Intent(this@HomeActivity, GameDetailActivity::class.java)
        intent.putExtra("game_id", id)


        startActivity(intent)

    }



}
