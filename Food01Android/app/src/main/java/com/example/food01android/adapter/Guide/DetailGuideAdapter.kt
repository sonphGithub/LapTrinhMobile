package com.example.food01android.adapter.Guide

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.activity.GuideTab.fragment.DetailGuideFragmentDirections

import com.example.food01android.databinding.*
import com.example.food01android.model.Guide.DetailGuideData
import com.squareup.picasso.Picasso

class DetailGuideAdapter(var guides: ArrayList<DetailGuideData>, val listener: ClickInterFace) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class DetailGuideHolder(val binding: RowCateGuideBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class MoreDetailGuideHolder(val binding: RowCateGuideMorepartBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    fun setGuide(value: ArrayList<DetailGuideData>) {
        guides = value
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (guides[position].parts.isEmpty()) {
            1
        } else {
            2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val binding =
                RowCateGuideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DetailGuideHolder(binding, parent)
        } else {
            val binding = RowCateGuideMorepartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MoreDetailGuideHolder(binding, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is DetailGuideHolder -> {
                holder.binding.txtTitle.text = guides[position].title
                Picasso.get().load(guides[position].image).into(holder.binding.imageView)
                holder.binding.imageView.setOnClickListener {
                    listener.onClick(position)
                }
            }

            is MoreDetailGuideHolder -> {
                holder.binding.txtTitle.text = guides[position].title

                val adapter = PartGuideAdapter(guides[position].parts, object :
                    ClickInterFace {
                    override fun onClick(position: Int) {
                        val action =
                            DetailGuideFragmentDirections.actionNavigationDetailGuideToNavigationArticle(
                                guides[holder.adapterPosition].parts[position],
                                guides[position].part
                            )
                        Navigation.findNavController(holder.itemView).navigate(action)
                    }
                })

                holder.binding.listPart.layoutManager = LinearLayoutManager(
                    holder.parent.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                holder.binding.listPart.adapter = adapter
            }
        }
    }

    override fun getItemCount(): Int {
        return guides.size
    }

}


