package com.example.food01android.adapter.Favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.R
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.bounce
import com.example.food01android.databinding.RowFavoriteBinding
import com.example.food01android.model.Home.DataRecipeDetail
import com.squareup.picasso.Picasso

class CommunityAdapter(
    var recipes: ArrayList<DataRecipeDetail>,
    val listenerItemClick: ClickInterFace,
    val listenerSave: SaveFavoriteInterface,
    val listenerRemove: RemoveFavoriteInterface
) : RecyclerView.Adapter<CommunityAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(val binding: RowFavoriteBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    fun setContent(value: ArrayList<DataRecipeDetail>) {
        recipes = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowFavoriteBinding.inflate(layoutInflater, parent, false)
        return FavoriteViewHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        Picasso.get().load(recipes[position].image)
            .into(holder.binding.imageView)
        holder.binding.txtName.text = recipes[position].name
        holder.binding.txtResult.text = recipes[position].result
        holder.binding.root.setOnClickListener {
            listenerItemClick.onClick(position)
        }
        //config
        if (recipes[position].is_favorite == 0) {
            holder.binding.btnFav.setImageResource(R.drawable.ic_favorite_deselect)
        } else {
//            holder.binding.btnFav.bounce(holder.parent.context)
            holder.binding.btnFav.setImageResource(R.drawable.ic_favorite_selected)
        }
//        //action
        holder.binding.btnFav.setOnClickListener {
            if (recipes[position].is_favorite == 0) {
                recipes[position].is_favorite = 1
//                holder.binding.btnFav.bounce(holder.parent.context)
                holder.binding.likeHeart.likeAnimation()
                holder.binding.btnFav.setImageResource(R.drawable.ic_favorite_selected)
                recipes[position].id?.let { it1 -> listenerSave.save(it1) }
            } else {
                recipes[position].is_favorite = 0
                holder.binding.btnFav.setImageResource(R.drawable.ic_favorite_deselect)
                recipes[position].id?.let { it1 -> listenerRemove.remove(it1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    interface SaveFavoriteInterface {
        fun save(id: Int)
    }

    interface RemoveFavoriteInterface {
        fun remove(id: Int)
    }

}
