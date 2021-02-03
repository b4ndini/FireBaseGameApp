package com.example.digitalhousegameapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.digitalhousegameapp.R
import com.example.digitalhousegameapp.model.Game
import com.example.digitalhousegameapp.view.OnClickListenerInterface
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView


class HomeAdapter(
    options: FirestoreRecyclerOptions<Game>, private var listener: OnClickListenerInterface):
    FirestoreRecyclerAdapter<Game, HomeAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Game) {
        holder.gameTitle.text = model.name
        holder.gameYear.text = model.date
        Glide.with(holder.itemView.context).load(model.image).into(holder.gameImage)
        holder.gameCard.setOnClickListener {
            Log.i("posicaoo", "Clicou no ${holder.adapterPosition}")
            // listener.onItemClicked(holder.adapterPosition)
            val position = holder.adapterPosition
            if (position != -1 && this.listener != null) {
                listener.onItemClicked(snapshots.getSnapshot(position), position)
            }
        }
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
         var gameCard: MaterialCardView = itemView.findViewById(R.id.CardContainer)



     }

    fun setOnItemClickListener(listening : OnClickListenerInterface) {
         listener = listening
    }








    }


