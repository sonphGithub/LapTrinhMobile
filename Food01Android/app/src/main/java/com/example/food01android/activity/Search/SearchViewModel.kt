package com.example.food01android.activity.Search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Favorite.Favorite
import com.example.food01android.model.Home.Data
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Search.DataSearch
import com.example.food01android.model.Search.MoreSearch
import com.example.food01android.model.Search.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    var token: String = ""
    var nextPageUrl: String = ""
    var recipesLiveData: MutableLiveData<ArrayList<DataRecipeDetail>>? = null
    var suggestsLiveData: MutableLiveData<ArrayList<DataSearch>>? = null
    var suggets: ArrayList<DataSearch> = arrayListOf()
    var recipes: ArrayList<DataRecipeDetail> = arrayListOf()

    fun getSearchLiveData(): MutableLiveData<ArrayList<DataRecipeDetail>>? {
        if (recipes.isEmpty()) {
            recipesLiveData = MutableLiveData()
        }
        return recipesLiveData
    }

    fun getSuggetLiveData(): MutableLiveData<ArrayList<DataSearch>>? {
        if (suggets.isEmpty()) {
            suggestsLiveData = MutableLiveData()
            getSuggest()
        }
        return suggestsLiveData
    }


    fun getSuggest() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getSuggestKeyword(token).enqueue(object : Callback<Search> {
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                response.body()?.let { res ->

                    suggets = res.data
                    suggestsLiveData?.postValue(suggets)
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.d("fail", "" + t)
            }
        })
    }

    fun getSearch(keyword: String) {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getSearchResult(token, keyword).enqueue(object : Callback<Favorite> {
            override fun onResponse(call: Call<Favorite>, response: Response<Favorite>) {
                response.body()?.let { res ->
                    Log.e("search key", keyword)
                    Log.e("search result", "" + res.data)
                    recipes = res.data
                    recipesLiveData?.postValue(recipes)
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
                response.body()?.let { res ->
                    recipes.addAll(res.data)
                    recipesLiveData?.postValue(recipes)
                    nextPageUrl = res.next_page_url
                }
            }

            override fun onFailure(call: Call<MoreSearch>, t: Throwable) {
                Log.d("fail", "" + t)

            }
        })
    }

}