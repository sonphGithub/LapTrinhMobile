package com.example.food01android.adapter.ShoppingList

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.R
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.bounce

import com.example.food01android.databinding.RowItemShoppingListBinding
//import com.example.food01android.manager.RecipeObject
import com.example.food01android.model.Home.DataRecipeDetail
import com.squareup.picasso.Picasso

interface ShoppingListInterface{
    fun onClick(position: Int)
    fun onClickDelete(id: Int, position: Int)
    fun onClickShare(id: Int, recipeName: String)
}


class ShoppingListAdapter(var recipes: ArrayList<DataRecipeDetail>, val listener: ShoppingListInterface) :
    RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder>() {

    class ShoppingListHolder(val binding: RowItemShoppingListBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    fun setContent(value: ArrayList<DataRecipeDetail>) {
        recipes = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder {
        val binding = RowItemShoppingListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShoppingListHolder(binding, parent)
    }

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
        val resultNumeric = recipes[position].result?.filter { it.isDigit() }
        var resultInt = resultNumeric?.toInt()
        val resultString = recipes[position].result?.replace(resultNumeric.toString(), "")
        holder.binding.txtName.isSelected = true
        holder.binding.txtName1.isSelected = true
        holder.binding.txtResult.text =  resultString
        holder.binding.txtNumber.text = resultInt.toString()
        holder.binding.txtName.text =  recipes[position].name
        holder.binding.txtName1.text =  recipes[position].name
        holder.binding.txtResult1.text =  recipes[position].result

        if (recipes.isEmpty()){
            holder.binding.imageView1.setImageResource(R.drawable.image_demo_recipes)
            holder.binding.imageView.setImageResource(R.drawable.image_demo_recipes)
        }else{
            Picasso.get().load(recipes[position].image)
                .into(holder.binding.imageView1)
            Picasso.get().load(recipes[position].image)
                .into(holder.binding.imageView)
        }

        holder.binding.btnMore.setOnClickListener {
            holder.binding.swipeRevealLayout1.open(true)
        }

        holder.binding.viewContent1.setOnClickListener{
            if (holder.binding.swipeRevealLayout1.isOpened){
                holder.binding.swipeRevealLayout1.close(true)
            }
        }


        holder.binding.btnClose.setOnClickListener {
            holder.binding.cellContentView.visibility = View.GONE
            holder.binding.foldingCell.toggle(false)
        }

        holder.binding.btnCong.setOnClickListener{
            resultInt = resultInt?.plus(1)
            holder.binding.txtNumber.text = resultInt.toString()
            holder.binding.txtNumber.bounce(holder.parent.context)
        }

        holder.binding.btnTru.setOnClickListener{
            resultInt = resultInt?.minus(1)
            holder.binding.txtNumber.text = resultInt.toString()
            holder.binding.txtNumber.bounce(holder.parent.context)
        }

        holder.binding.btnOpen1.setOnClickListener{
            holder.binding.foldingCell.toggle(false)
        }

//        holder.binding.deleteLayout.setOnClickListener {
//            list.removeAt(holder.adapterPosition)
//            notifyItemRemoved(holder.adapterPosition)
//        }

//        holder.binding.viewContent.setOnClickListener {
//            listener.onClick(position)
//        }

        holder.binding.listIngredients.layoutManager =
            LinearLayoutManager(holder.parent.context, LinearLayoutManager.VERTICAL, false)
        holder.binding.listIngredients.adapter =
            IngredientShoppingListAdapter(recipes[position].ingredients_list, object : ClickInterFace {
                override fun onClick(position: Int) {

                }
            })

        holder.binding.deleteLayout.setOnClickListener{
            recipes[position].id?.let { it1 -> listener.onClickDelete(it1, position) }
        }

        holder.binding.shareLayout.setOnClickListener{
//            recipes[position].id?.let { it1 -> recipes[position].name?.let { it2 ->
//                listener.onClickShare(it1,
//                    it2
//                )
//            } }

            Intent(Intent.ACTION_SEND).apply {
                // The intent does not have a URI, so declare the "text/plain" MIME type
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("jan@example.com")) // recipients
                putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                putExtra(Intent.EXTRA_TEXT, "Email message text")
                putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
                // You can also attach multiple items by passing an ArrayList of Uris
                            holder.parent.context.startActivity(this)
            }


        }



    }

    override fun getItemCount(): Int {
        return recipes.size
    }

}