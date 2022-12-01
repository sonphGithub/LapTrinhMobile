package com.example.food01android.adapter.Guide

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.databinding.RowGuideBinding
import com.example.food01android.model.Guide.Guide
import com.squareup.picasso.Picasso

class GuideAdapter(var guides: ArrayList<Guide> = arrayListOf(), val listener: ClickInterFace): RecyclerView.Adapter<GuideAdapter.GuideListHolder>() {

    class GuideListHolder(val binding: RowGuideBinding, val parent: ViewGroup) : RecyclerView.ViewHolder(binding.root)


    fun setGuide(value: ArrayList<Guide>) {
        guides = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowGuideBinding.inflate(layoutInflater, parent, false)
        return GuideListHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: GuideListHolder, position: Int) {

        Picasso.get().load(guides[position].image)
            .into(holder.binding.imgViewGuide)
        holder.binding.tvTitleGuide.text = guides[position].name

        holder.binding.root.setOnClickListener{
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return guides.size
    }

}


interface ClickInterFace{
    fun onClick(position: Int)
}

