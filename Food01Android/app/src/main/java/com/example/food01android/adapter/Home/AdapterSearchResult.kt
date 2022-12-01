package com.example.food01android.adapter.Home

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.icu.lang.UProperty.INT_START
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.databinding.RowSearchResultBinding
import com.example.food01android.model.Home.DataRecipeDetail
import com.squareup.picasso.Picasso


class AdapterSearchResult(
    var results: ArrayList<DataRecipeDetail>,
    val listener: ClickInterFace
) :
    RecyclerView.Adapter<AdapterSearchResult.SearchResultHolder>() {

    class SearchResultHolder(val binding: RowSearchResultBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    fun setContent(value: ArrayList<DataRecipeDetail>) {
        results = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowSearchResultBinding.inflate(layoutInflater, parent, false)
        return SearchResultHolder(binding, parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {

        holder.binding.txtName.text = results[position].name
        Picasso.get().load(results[position].image)
            .into(holder.binding.imgViewSearch)

        val ingrNumber = results[position].ingredients_list.size

        holder.binding.txtIngredient.text =
            Html.fromHtml("<b>" + ingrNumber + "</b>"+ " Ingredient(s)")

        holder.binding.txtTotalTime.text = results[position].total_time

        if (results[position].nutritions_list.isNotEmpty()) {
            results[position].nutritions_list[0].name.let {
                val calo = it.substringAfterLast(",")
                holder.binding.txtCalo.text = Html.fromHtml("<b>" + calo + "</b> " + "Calorie(s)")
            }
        } else {
            holder.binding.txtCalo.text = "Calorie(s)"
        }





        holder.binding.root.setOnClickListener {
            listener.onClick(position)
        }


    }

    override fun getItemCount(): Int {
        return results.size
    }
}