package com.example.food01android.adapter.Detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.example.food01android.databinding.*
import com.example.food01android.model.Home.DataRecipeDetail
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import com.example.food01android.R
import com.example.food01android.activity.HomeTab.Detail.DetailRecipesFragmentDirections
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.adapter.Home.AdapterCategories
import com.example.food01android.manager.DBManager
import com.example.food01android.safeNavigate
import io.realm.Realm

import java.lang.Exception

enum class ShowNutritionMode {
    showNutrition,
    hideNutrition
}

class AdapterDetailRecipes(
    val recipesDetail: DataRecipeDetail,
    var recommends: ArrayList<DataRecipeDetail>

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dbManager: DBManager? = null

    init {
        dbManager = DBManager()
    }


    class RowAboutHolder(val binding: DetailRowAboutBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class RowTagHolder(val binding: DetailRowTagBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class RowNutritionHolder(val binding: DetailRowNutritionsBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class RowIngredientHolder(val binding: DetailRowIngredientsBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class RowStepByStepHolder(val binding: DetailRowPreparationBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class RowReviewHolder(val binding: DetailRowReviewsBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class RowRecommendationHolder(val binding: HomeTrendingRowBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    fun setRecommend(value: ArrayList<DataRecipeDetail>) {
        recommends = value
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return 1
        } else if (position == 1) {
            return 2
        } else if (position == 2) {
            return 3
        } else if (position == 3) {
            return 4
        } else if (position == 4) {
            return 5
        } else if (position == 5) {
            return 6
        } else if (position == 6) {
            return 7
        } else {
            return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if (viewType == 1) {
            val binding =
                DetailRowTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RowTagHolder(binding, parent)
        } else if (viewType == 2) {
            val binding = DetailRowAboutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RowAboutHolder(binding, parent)
        } else if (viewType == 3) {
            val binding = DetailRowIngredientsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RowIngredientHolder(binding, parent)
        } else if (viewType == 4) {
            val binding = DetailRowNutritionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RowNutritionHolder(binding, parent)
        } else if (viewType == 5) {
            val binding = DetailRowPreparationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RowStepByStepHolder(binding, parent)
        } else if (viewType == 6) {
            val binding = DetailRowReviewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RowReviewHolder(binding, parent)
        } else if (viewType == 7) {
            val binding = HomeTrendingRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return RowRecommendationHolder(binding, parent)
        }
        val binding = HomeTrendingRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RowRecommendationHolder(binding, parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RowTagHolder -> {
                val nameList = recipesDetail.tags?.split(",")
                holder.binding.tagcontainerLayout.tagBackgroundColor = Color.WHITE
                holder.binding.tagcontainerLayout.tagBorderColor = Color.parseColor("#5BC25A")
                holder.binding.tagcontainerLayout.backgroundColor = Color.WHITE
                holder.binding.tagcontainerLayout.borderColor = Color.WHITE
                holder.binding.tagcontainerLayout.removeAllTags()
                holder.binding.tagcontainerLayout.setTags(nameList)
            }

            is RowAboutHolder -> {
                if (recipesDetail.about == null) {
                    holder.binding.txtAboutDescription.text = "this content will be updated soon..."
                } else {
                    holder.binding.txtAboutDescription.text = recipesDetail.about
                }
            }

            is RowIngredientHolder -> {
                val adapter = AdapterIngredients(recipesDetail.ingredients_list)
                holder.binding.listIngredient.layoutManager =
                    LinearLayoutManager(
                        holder.parent.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                holder.binding.listIngredient.adapter = adapter
                holder.binding.listIngredient.isNestedScrollingEnabled = true
                holder.binding.viewAddShoppingList.setOnClickListener {
//                    val shopModel: ShoppingList? = null
//                    Realm.init(holder.parent.context)
////                    val config = RealmConfiguration.Builder().name()
//                    val realm: Realm? = Realm.getDefaultInstance()
//                    realm!!.beginTransaction()
//                    try {
//                        shopModel?.id = recipesDetail.id
//                        shopModel?.result = recipesDetail.result
//                        shopModel?.image = recipesDetail.image
//                        shopModel?.name = recipesDetail.name
////                        realm!!.copyToRealm(shopModel)
//                        realm?.commitTransaction()
//                        Log.e("realm", "save realm success")
//                    } catch (e: Exception) {
//                        Log.e("realm", "save realm fail" + e)
//                    }

                        dbManager?.addRecipeToShoppingList(recipesDetail)
                }
            }

            is RowNutritionHolder -> {

                if (recipesDetail.nutritions_list.isEmpty()){
                    holder.binding.listNutrition.visibility = View.GONE
                    holder.binding.txtDescription.visibility = View.VISIBLE
                }else{
                    holder.binding.listNutrition.visibility = View.VISIBLE
                    holder.binding.txtDescription.visibility = View.GONE
                }

                holder.binding.listNutrition.layoutManager = LinearLayoutManager(
                    holder.parent.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                holder.binding.listNutrition.adapter =
                    AdapterNutrition(recipesDetail.nutritions_list)
                //configShowNutrition
                var mode: ShowNutritionMode? = ShowNutritionMode.showNutrition

                if (mode == ShowNutritionMode.showNutrition) {
                    holder.binding.btnHide.text = "Hide"
                    holder.binding.listNutrition.layoutAnimation =
                        AnimationUtils.loadLayoutAnimation(
                            holder.parent.context,
                            R.anim.layout_animation_down_to_up
                        )
                    holder.binding.listNutrition.visibility = View.VISIBLE
                    mode = ShowNutritionMode.hideNutrition
                } else {
                    holder.binding.btnHide.text = "Show"
                    holder.binding.listNutrition.visibility = View.GONE
                    mode = ShowNutritionMode.showNutrition
                }


                holder.binding.btnHide.setOnClickListener {
                    if (mode == ShowNutritionMode.showNutrition) {
                        holder.binding.btnHide.text = "Hide"
                        holder.binding.listNutrition.layoutAnimation =
                            AnimationUtils.loadLayoutAnimation(
                                holder.parent.context,
                                R.anim.layout_animation_down_to_up
                            )
                        holder.binding.listNutrition.visibility = View.VISIBLE
                        mode = ShowNutritionMode.hideNutrition
                    } else {
                        holder.binding.btnHide.text = "Show"
                        holder.binding.listNutrition.visibility = View.GONE
                        mode = ShowNutritionMode.showNutrition
                    }
                }
            }

            is RowStepByStepHolder -> {
                holder.binding.btnStepByStep.setOnClickListener {
                    val action =
                        DetailRecipesFragmentDirections.actionNavigationDetailRecipesToNavigationPrepation(
                            recipesDetail.methods_list.toTypedArray()
                        )
                    Navigation.findNavController(holder.itemView).safeNavigate(action)
                }
            }

            is RowReviewHolder -> {

                holder.binding.tvReviews.text = "Review(s) (${recipesDetail.reviews.size})"
                holder.binding.ratingbarReview.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                    val action =
                        DetailRecipesFragmentDirections.actionNavigationDetailRecipesToNavigationReview(
                            recipesDetail,
                            rating
                        )
                    Navigation.findNavController(holder.itemView).navigate(action)
                }
            }

            is RowRecommendationHolder -> {

                holder.binding.txtTitle.text = "Recommendation"
                    Log.e("RECOMMENDD", "" + recommends)
                val adapter =
                    AdapterCategories(
                        recommends,
                        object : ClickInterFace {
                            override fun onClick(position: Int){
                                val action =
                                    recommends[position].id?.let {
                                        DetailRecipesFragmentDirections.actionNavigationDetailRecipesToNavigationPreview(
                                            it
                                        )
                                    }
                                action?.let {
                                    Navigation.findNavController(holder.itemView).navigate(
                                        it
                                    )
                                }

                            }
                        })

                holder.binding.listView.layoutManager =
                    LinearLayoutManager(
                        holder.parent.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                holder.binding.listView.adapter = adapter
            }
        }
    }

    override fun getItemCount(): Int {
        return 7
    }



}