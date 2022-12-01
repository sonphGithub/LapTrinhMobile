package com.example.food01android.adapter.Detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.databinding.ItemCategoriesBinding
import com.example.food01android.databinding.RowReviewsBinding
import com.example.food01android.model.Home.Review

class AdapterRecyclerViewReviews(val reviews: ArrayList<Review>) :
    RecyclerView.Adapter<AdapterRecyclerViewReviews.ReviewHolder>() {

    class ReviewHolder(val binding: RowReviewsBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = RowReviewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReviewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        holder.binding.tViewUserName.text = reviews[position].author!!.name
        holder.binding.tViewContent.text = reviews[position].content
        holder.binding.ratingBarBottom.rating = reviews[position].star.toFloat()
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}