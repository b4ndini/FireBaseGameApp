package com.example.digitalhousegameapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digitalhousegameapp.R
import com.example.digitalhousegameapp.model.Game
import com.example.digitalhousegameapp.view.OnClickListenerInterface
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class HomeAdapter(options: FirestoreRecyclerOptions<Game>):
    FirestoreRecyclerAdapter<Game, HomeAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Game) {
        holder.gameTitle.text = model.name
        holder.gameYear.text = model.date
      //  holder.gameDescription = model.description
        //holder.gameImage.text = model.image
        Glide.with(holder.itemView.context).load(model.image).into(holder.gameImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.game_card_view_item,
            parent, false
        )
        return ViewHolder(v)
    }

     class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


         var gameTitle: TextView = itemView.findViewById(R.id.tvGameFrameTitle)
         var gameYear: TextView = itemView.findViewById(R.id.tvReleaseYear)
         var gameImage: ImageView = itemView.findViewById(R.id.ivGameFrameImage)



     }









    }


