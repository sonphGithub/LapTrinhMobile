package com.example.food01android.adapter.Home

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.databinding.RowKeywordSuggestionBinding

import androidx.core.content.res.ResourcesCompat
import com.example.food01android.R
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.example.food01android.model.Search.DataSearch

class AdapterSuggestKey(var suggests: ArrayList<DataSearch>, val listener: TagClickInterface) :
    RecyclerView.Adapter<AdapterSuggestKey.SearchHolder>() {

    class SearchHolder(val binding: RowKeywordSuggestionBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    fun setContent(value: ArrayList<DataSearch>){
        suggests = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchHolder {
        val binding =
            RowKeywordSuggestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(binding, parent)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val typeface = ResourcesCompat.getFont(holder.parent.context, R.font.helvetica_regular)

        holder.binding.tViewType.text = suggests[position].type
        holder.binding.tagView.apply {
            tagTextColor = Color.parseColor("#4A4A4A")
            tagBackgroundColor = Color.WHITE
            tagBorderColor = Color.WHITE
            backgroundColor = Color.parseColor("#F3F3F3")
            borderColor = Color.parseColor("#F3F3F3")
            borderWidth = 0F
            tagTypeface = typeface
            removeAllTags()
            tags = suggests[position].keywords

            setOnTagClickListener(object : OnTagClickListener {
                override fun onTagClick(position: Int, text: String) {
                    listener.tagClick(holder.binding.tagView.getTagText(position))
                }

                override fun onTagLongClick(position: Int, text: String?) {

                }

                override fun onSelectedTagDrag(position: Int, text: String?) {

                }

                override fun onTagCrossClick(position: Int) {

                }
            })
        }



    }

    override fun getItemCount(): Int {
        return suggests.size
    }

    interface TagClickInterface {
        fun tagClick(keyword: String)
    }
}