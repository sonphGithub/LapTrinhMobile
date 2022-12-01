package com.example.food01android.adapter.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.databinding.RowExploreBinding
import com.example.food01android.model.Home.Explore
import com.squareup.picasso.Picasso

class AdapterExplore(val explores: ArrayList<Explore>, val listener: ClickInterFace) :
    RecyclerView.Adapter<AdapterExplore.HomeExploreHolder>() {

    class HomeExploreHolder(val binding: RowExploreBinding, val parent: ViewGroup) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeExploreHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RowExploreBinding.inflate(layoutInflater, parent, false)
            return HomeExploreHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: HomeExploreHolder, position: Int) {
        holder.binding.txtName.setText(explores[position].name)
        Picasso.get().load(explores[position].image)
            .into(holder.binding.imageViewExplore)

        holder.binding.root.setOnClickListener {
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return explores.size
    }

}