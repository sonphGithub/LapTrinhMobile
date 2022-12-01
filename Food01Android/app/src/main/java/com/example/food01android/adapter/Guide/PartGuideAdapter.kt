package com.example.food01android.adapter.Guide

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.databinding.*
import com.example.food01android.model.Guide.DetailGuideData
import com.squareup.picasso.Picasso
import com.trung.fitnessapp.utils.MetricsUtil

class PartGuideAdapter(val parts: ArrayList<DetailGuideData>, val listener: ClickInterFace) :
    RecyclerView.Adapter<PartGuideAdapter.GuideMorePartHolder>() {

    class GuideMorePartHolder(val binding: ItemMorePartBinding, val parent: ViewGroup) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideMorePartHolder {
        val binding = ItemMorePartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuideMorePartHolder(binding, parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GuideMorePartHolder, position: Int) {

        val widthDevice = holder.parent.context.resources.displayMetrics.widthPixels
        val widthItem2 = widthDevice - (MetricsUtil.convertDpToPixel(44F, holder.parent.context))
        val widthItem3 = widthDevice - (MetricsUtil.convertDpToPixel(56F, holder.parent.context))

        val height  = MetricsUtil.convertDpToPixel(170F, holder.parent.context)
        val margin =  MetricsUtil.convertDpToPixel(6F, holder.parent.context)


        if (parts.size > 2){
            val param = holder.binding.cardView.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(margin.toInt(),0,margin.toInt(),0)
            param.width = widthItem3.toInt() / 3
            param.height = height.toInt()
            holder.binding.cardView.layoutParams = param

        }else{
            val param = holder.binding.cardView.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(margin.toInt(),0,margin.toInt(),0)
            param.width = widthItem2.toInt() / 2
            param.height = height.toInt()
            holder.binding.cardView.layoutParams = param
        }


        Picasso.get().load(parts[position].image)
            .into(holder.binding.imgViewPart)
        holder.binding.txtPart.text = "Part " + parts[position].part

        holder.binding.root.setOnClickListener{
            listener.onClick(position)
        }
    }

    override fun getItemCount(): Int {
        return parts.size
    }

}
