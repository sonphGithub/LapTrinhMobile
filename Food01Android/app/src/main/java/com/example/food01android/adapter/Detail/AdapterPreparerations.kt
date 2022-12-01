package com.example.food01android.adapter.Detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter


import com.example.food01android.databinding.ItemCategoriesBinding
import com.example.food01android.databinding.ViewPreparationBinding
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Home.Methods

class AdapterViewPagerPrepareration(var method: ArrayList<Methods>): RecyclerView.Adapter<AdapterViewPagerPrepareration.MyViewHolder>() {

    class MyViewHolder(val binding: ViewPreparationBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ViewPreparationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtPreparation.text = method[position].name
    }

    override fun getItemCount(): Int {
        return method.size
    }


}