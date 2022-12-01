package com.example.food01android.adapter.ShoppingList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.databinding.IngredientsShoppinglistItemBinding
import com.example.food01android.databinding.RowItemShoppingListBinding
import com.example.food01android.model.Home.Ingredients

class IngredientShoppingListAdapter( var ingredients: ArrayList<Ingredients>, val listener: ClickInterFace):
RecyclerView.Adapter<IngredientShoppingListAdapter.IngredientHolder>() {

//    var ingredients: ArrayList<String> = arrayListOf("sad", "ádas", "ádas", "ádas", "ádas")

    class IngredientHolder(val binding: IngredientsShoppinglistItemBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientHolder {
        val binding = IngredientsShoppinglistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: IngredientHolder, position: Int) {
        holder.binding.txtIngredients.text = ingredients[position].name
        holder.binding.root.setOnClickListener{
            listener.onClick(position)
        }
        holder.binding.btnMore.setOnClickListener {
            holder.binding.swipeRevealLayout.open(true)
        }
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}