package com.example.food01android.adapter.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.databinding.RowNutritionsBinding
import com.example.food01android.model.Home.Ingredients

class AdapterNutrition(val nutritionList: ArrayList<Ingredients>) :
    RecyclerView.Adapter<AdapterNutrition.RowNutritionHoldwer>() {

    class RowNutritionHoldwer(val binding: RowNutritionsBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowNutritionHoldwer {
        val binding =
            RowNutritionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowNutritionHoldwer(binding, parent)
    }

    override fun onBindViewHolder(holder: RowNutritionHoldwer, position: Int) {
        val nameList = nutritionList[position].name?.split(",")
        nameList?.let { list ->
            val mea: String = list[1]
            val nutr: String = list[0]
            holder.binding.txtMeasure.text = mea
            holder.binding.txtNutrition.text = nutr
        }


        if (position == nutritionList.lastIndex) {
            holder.binding.dashedLine.visibility = View.INVISIBLE
        } else {
            holder.binding.dashedLine.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return nutritionList.size
    }

}