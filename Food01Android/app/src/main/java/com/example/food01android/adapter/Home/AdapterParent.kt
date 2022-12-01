package com.example.food01android.adapter.Home


import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food01android.R
import com.example.food01android.activity.HomeTab.Home.HomeFragmentDirections
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.databinding.HomeExploreRowBinding
import com.example.food01android.databinding.HomeFindRecipesRowBinding
import com.example.food01android.databinding.HomeTopRowBinding
import com.example.food01android.databinding.HomeTrendingRowBinding
import com.example.food01android.model.Home.Category
import com.example.food01android.model.Home.DataX


class AdapterParent(var data: DataX = DataX(), var categories: ArrayList<Category>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var adapterExplore: AdapterExplore
    lateinit var adapterTop: AdapterTop
    lateinit var adapterTrending: AdapterCategories



    class TopViewHolder(val binding: HomeTopRowBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class ExploreViewHolder(val binding: HomeExploreRowBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class FindRecipeViewHolder(val binding: HomeFindRecipesRowBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class TrendingHolder(val binding: HomeTrendingRowBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    class CategoriesHolder(val binding: HomeTrendingRowBinding, val parent: ViewGroup) :
        RecyclerView.ViewHolder(binding.root)

    fun setHome(value: DataX) {
        data = value
        categories = value.categories
        notifyDataSetChanged()
    }

    fun setCate(value: ArrayList<Category>) {
        categories = value
        notifyItemChanged(3)
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
        } else {
            return 5
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding =
                HomeTopRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TopViewHolder(binding, parent)
        } else if (viewType == 2) {
            val binding = HomeExploreRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ExploreViewHolder(binding, parent)
        } else if (viewType == 3) {
            val binding = HomeFindRecipesRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return FindRecipeViewHolder(binding, parent)
        } else if (viewType == 4) {
            val binding = HomeTrendingRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TrendingHolder(binding, parent)
        } else if (viewType == 5) {
            val binding = HomeTrendingRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CategoriesHolder(binding, parent)
        } else {
            val binding = HomeTrendingRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CategoriesHolder(binding, parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TopViewHolder -> {
                adapterTop =
                    AdapterTop(data.top, true, object : ClickInterFace {
                        override fun onClick(position: Int) {
                            val id: Int = data.top[position].id
                            val action =
                                HomeFragmentDirections.actionNavigationHomeToNavigationPreview(
                                    recipeId = id
                                )
                            Navigation.findNavController(holder.itemView).navigate(action)
                        }
                    })
                holder.binding.viewPager.adapter = adapterTop
                holder.binding.indicator.apply {
                    highlighterViewDelegate = {
                        val highlighter = View(holder.parent.context)
                        highlighter.background =
                            getDrawable(holder.parent.context, R.drawable.dot_select)
                        highlighter.layoutParams = FrameLayout.LayoutParams(8.dp(), 8.dp())
                        highlighter
                    }

                    unselectedViewDelegate = {
                        val unselected = View(holder.parent.context)
                        unselected.background =
                            getDrawable(holder.parent.context, R.drawable.dot_unselect)
                        unselected.layoutParams = LinearLayout.LayoutParams(8.dp(), 8.dp())
                        unselected.alpha = 0.4f
                        unselected
                    }

                    updateIndicatorCounts(data.top.size)
                }

                holder.binding.viewPager.onIndicatorProgress = { selectingPosition, progress ->
                    holder.binding.indicator.onPageScrolled(
                        selectingPosition,
                        progress
                    )
                }
//                holder.binding.indicator.updateIndicatorCounts(holder.binding.viewPager.indicatorCount)
//                                holder.binding.viewPager.setPageTransformer(false, ZoomOutPageTransformer())
            }

            is ExploreViewHolder -> {
                adapterExplore =
                    AdapterExplore(data.explore, object : ClickInterFace {
                        override fun onClick(position: Int) {
                            val categoryID = data.explore[position].id
                            val title: String = data.explore[position].name
                            val action =
                                HomeFragmentDirections.actionNavigationHomeToNavigationExplore(
                                    categoryId = categoryID,
                                    title = title
                                )
                            Navigation.findNavController(holder.itemView).navigate(action)
                        }
                    })
                holder.binding.recyclerViewExplore.layoutManager =
                    LinearLayoutManager(
                        holder.parent.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                holder.binding.recyclerViewExplore.adapter = adapterExplore
            }

            is TrendingHolder -> {
                adapterTrending = AdapterCategories(data.trending,
                    object : ClickInterFace {
                        override fun onClick(position: Int) {
                            val recipe_id = data.trending[position].id
                            val action =
                                recipe_id?.let {
                                    HomeFragmentDirections.actionNavigationHomeToNavigationPreview(
                                        it
                                    )
                                }
                            if (action != null) {
                                Navigation.findNavController(holder.itemView).navigate(action)
                            }
                        }
                    })
                holder.binding.listView.layoutManager = LinearLayoutManager(
                    holder.parent.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                holder.binding.listView.adapter = adapterTrending


            }

            is CategoriesHolder -> {
                holder.binding.txtTitle.setText(data.categories[position - 4].name)
                adapterTrending =
                    AdapterCategories(data.categories[position - 4].recipes,
                        object : ClickInterFace {
                            override fun onClick(position: Int) {
                                val recipe_id = data.categories[holder.adapterPosition - 4].recipes[position].id
                                val action =
                                    recipe_id?.let {
                                        HomeFragmentDirections.actionNavigationHomeToNavigationPreview(
                                            it
                                        )
                                    }
                                if (action != null) {
                                    Navigation.findNavController(holder.itemView).navigate(action)
                                }
                            }
                        })
                holder.binding.listView.layoutManager = LinearLayoutManager(
                    holder.parent.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                holder.binding.listView.adapter = adapterTrending
            }
        }
    }

    override fun getItemCount(): Int {
        return data.categories.size + 4
    }

}


fun Int.dp(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}

fun View.inflate(resId: Int, container: ViewGroup, attach: Boolean): View {
    return LayoutInflater.from(this.context).inflate(resId, container, attach)
}



