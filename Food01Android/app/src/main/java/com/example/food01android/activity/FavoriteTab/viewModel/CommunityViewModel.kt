package com.example.food01android.activity.FavoriteTab.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.activity.FavoriteTab.FavoriteMode
import com.example.food01android.model.Favorite.Favorite
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Home.MoreCategory
import com.example.food01android.model.Search.MoreSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityViewModel : ViewModel() {
    var recipesLData: MutableLiveData<ArrayList<DataRecipeDetail>>? = null
    var recipes: ArrayList<DataRecipeDetail> = arrayListOf()
    var token: String = ""
    var nextPageUrl: String = ""

    fun getCommunityLiveData(): MutableLiveData<ArrayList<DataRecipeDetail>>? {
        if (recipes.isEmpty()){
            recipesLData = MutableLiveData()
            getCommunity()
        }
        return recipesLData
    }

    fun getCommunity() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getCommunity(token).enqueue(object : Callback<Favorite> {
            override fun onResponse(call: Call<Favorite>, response: Response<Favorite>) {
                response.body()?.let { res ->
                    recipes = res.data
                    recipesLData?.postValue(recipes)
                    nextPageUrl = res.next_page_url
                }
            }

            override fun onFailure(call: Call<Favorite>, t: Throwable) {
                Log.d("fail", "" + t)
            }
        })
    }

    fun loadMore() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getMoreSearch(token, nextPageUrl).enqueue(object : Callback<MoreSearch> {
            override fun onResponse(call: Call<MoreSearch>, response: Response<MoreSearch>) {
                if (nextPageUrl != "") {
                    response.body()?.let {
                        recipes.addAll(it.data)
                        recipesLData?.postValue(recipes)
                        nextPageUrl = it.next_page_url
                    }
                }
            }

            override fun onFailure(call: Call<MoreSearch>, t: Throwable) {
                Log.d("Home", "error get more cate " + t)
                recipesLData?.postValue(arrayListOf())
            }
        })

    }

}




