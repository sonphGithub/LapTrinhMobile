package com.example.food01android.adapter.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.R
import com.example.food01android.databinding.ItemCategoriesBinding
import com.example.food01android.databinding.RowIngredientsBinding
import com.example.food01android.model.Home.Ingredients

class AdapterIngredients(val ingredients: ArrayList<Ingredients>) : RecyclerView.Adapter<AdapterIngredients.IngredientsHolder>(){

    class IngredientsHolder(val binding: RowIngredientsBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IngredientsHolder {
        val binding = RowIngredientsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IngredientsHolder(binding, parent)
    }

    override fun onBindViewHolder(
        holder: IngredientsHolder,
        position: Int
    ) {
        holder.binding.tvNameIngredient.text = ingredients[position].name
    }

    override fun getItemCount(): Int {
        return  ingredients.size
    }




}

//val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_ingredients, parent,false)
//        val tvName = view.findViewById<TextView>(R.id.tvNameIngredient)
////        val tvMeasure = view.findViewById<TextView>(R.id.tvMeasureIngredient)
////        val nameList = ingredientList[position].name?.split(",")
////        val mea: String = nameList!![0]
////        val ingr: String = nameList!![1]
////        Log.d("split", "ingre"+ ingr)
////        Log.d("split", "mea"+ mea)
//        tvName.text = ingredientList[position].name
////        tvMeasure.text = mea
