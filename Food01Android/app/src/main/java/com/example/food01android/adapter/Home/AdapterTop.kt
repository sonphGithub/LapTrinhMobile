package com.example.food01android.adapter.Home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.example.food01android.R
import com.example.food01android.adapter.Guide.ClickInterFace


import com.example.food01android.model.Home.Top
import com.squareup.picasso.Picasso

class AdapterTop(
    itemList: ArrayList<Top>,
    isInfinite: Boolean,
    val listener: ClickInterFace
) : LoopingPagerAdapter<Top>(itemList, isInfinite) {

    override fun inflateView(
        viewType: Int,
        container: ViewGroup,
        listPosition: Int
    ): View {
        return LayoutInflater.from(container.context).inflate(R.layout.row_top, container, false)
    }

    override fun bindView(
        convertView: View,
        listPosition: Int,
        viewType: Int
    ) {
        val description = convertView.findViewById<TextView>(R.id.tvNameRecommend)
        val imageView: ImageView = convertView.findViewById(R.id.imageViewTop)
        description.text = itemList?.get(listPosition)?.name.toString()
        Picasso.get().load(itemList?.get(listPosition)?.image.toString())
            .into(imageView)
        convertView.setOnClickListener{
            listener.onClick(listPosition)
        }
    }
}



//class AdapterRecyclerViewTop(val topList: ArrayList<Top>, val listener: AdapterRecyclerViewTopInterface) :
//    RecyclerView.Adapter<AdapterRecyclerViewTop.HomeTopHolder>() {
//
//    class HomeTopHolder(val binding: RowTopBinding, val parent: ViewGroup) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTopHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = RowTopBinding.inflate(layoutInflater, parent, false)
//        return HomeTopHolder(binding, parent)
//    }
//
//    override fun onBindViewHolder(holder: HomeTopHolder, position: Int) {
//        holder.binding.tvNameRecommend.setText(topList[position].name)
//        Picasso.get().load(topList[position].image)
//            .into(holder.binding.imageViewTop)
//        holder.binding.root.setOnClickListener {
//            listener.onClickItemClicked(position)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return topList.size
//    }
//
//}

//class AdapterRecyclerViewTop(val topList: ArrayList<Top>, val listener: AdapterRecyclerViewTopInterface) :
//    RecyclerView.Adapter<AdapterRecyclerViewTop.HomeTopHolder>() {
//
//    class HomeTopHolder(val binding: RowTopBinding, val parent: ViewGroup) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTopHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = RowTopBinding.inflate(layoutInflater, parent, false)
//        return HomeTopHolder(binding, parent)
//    }
//
//    override fun onBindViewHolder(holder: HomeTopHolder, position: Int) {
//        holder.binding.tvNameRecommend.setText(topList[position].name)
//        Picasso.get().load(topList[position].image)
//            .into(holder.binding.imageViewTop)
//        holder.binding.root.setOnClickListener {
//            listener.onClickItemClicked(position)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return topList.size
//    }
//
//}