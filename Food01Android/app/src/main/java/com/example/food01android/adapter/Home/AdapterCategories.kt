package com.example.food01android.adapter.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.R
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.databinding.ItemCategoriesBinding
import com.example.food01android.databinding.RowExploreBinding
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Home.Explore
import com.example.food01android.model.Home.Recipe
import com.squareup.picasso.Picasso

class AdapterCategories(
    val recipes: ArrayList<DataRecipeDetail>,
    val listener: ClickInterFace
) : RecyclerView.Adapter<AdapterCategories.HomeCategoriesHolder>() {

    class HomeCategoriesHolder(val binding: ItemCategoriesBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoriesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoriesBinding.inflate(layoutInflater, parent, false)
        return HomeCategoriesHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: HomeCategoriesHolder, position: Int) {
        val recipe = recipes[position]
        Picasso.get().load(recipe.image)
            .placeholder(R.drawable.thumb_recipe_categories)
            .into(holder.binding.imageViewItemCategories)
        holder.binding.tvNameItemCategories.text = recipe.name
        holder.binding.root.setOnClickListener {
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}