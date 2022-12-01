package com.example.food01android.activity.FavoriteTab.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food01android.FoodApi
import com.example.food01android.activity.BaseFragment
import com.example.food01android.activity.FavoriteTab.FavoriteFragmentDirections
import com.example.food01android.activity.FavoriteTab.viewModel.MyCollectionViewModel
import com.example.food01android.activity.MainActivity
import com.example.food01android.adapter.Favorite.CommunityAdapter
import com.example.food01android.adapter.Guide.ClickInterFace
import com.example.food01android.databinding.FavoriteCommunityBinding
import com.example.food01android.model.Guide.GuideModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCollectionFragment: BaseFragment() {

    lateinit var binding: FavoriteCommunityBinding
    lateinit var viewModel: MyCollectionViewModel
    lateinit var adapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavoriteCommunityBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyCollectionViewModel::class.java]
        (activity as MainActivity).showBottomBar()
        setUpView()
        bind()
        return binding.root
    }

    private fun setUpView() {
        binding.loadMoreView.visibility = View.GONE
        adapter = CommunityAdapter(arrayListOf(), object :
            ClickInterFace {
            override fun onClick(position: Int) {
                val recipe_id = viewModel.recipes[position].id
                val action =
                    recipe_id?.let {
                        FavoriteFragmentDirections.actionNavigationFavoriteToNavigationPreview(
                            it
                        )
                    }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }
        }, object : CommunityAdapter.SaveFavoriteInterface {
            override fun save(id: Int) {
                saveFavorite(id)
            }
        }, object : CommunityAdapter.RemoveFavoriteInterface {
            override fun remove(id: Int) {
                removeFavorite(id)
            }
        })
        binding.listFavorite.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.listFavorite.adapter = adapter
        binding.swipeRefresh.isEnabled = true
        refresh()
    }

    private fun bind() {
        viewModel.token = token
        viewModel.getCollectionLiveData()?.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes.isEmpty()){
                binding.viewEmpty.visibility = View.VISIBLE
                binding.listFavorite.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
            }else{
                binding.listFavorite.visibility = View.VISIBLE
                binding.loadingView.visibility = View.GONE
                binding.viewEmpty.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                adapter.setContent(recipes)
            }
        })

    }

    private fun refresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.recipes.clear()
            adapter.notifyDataSetChanged()
            bind()
        }
    }

    fun saveFavorite(recipeId: Int) {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.saveFavorite(recipeId, token).enqueue(object : Callback<GuideModel> {
            override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                Log.d("save favorite success", "save" + response.body()?.code)
            }

            override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }

    fun removeFavorite(recipeId: Int) {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.removeFavorite(recipeId, token).enqueue(object : Callback<GuideModel> {
            override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                Log.d("remove favorite success", "remove success" + response.body()?.message)
            }

            override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                Log.d("post", "post fail" + t)
            }
        })
    }

}