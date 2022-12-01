package com.example.food01android.activity.FavoriteTab.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.activity.FavoriteTab.FavoriteMode
import com.example.food01android.model.Favorite.Favorite
import com.example.food01android.model.Home.DataRecipeDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCollectionViewModel : ViewModel() {

    var recipesLData: MutableLiveData<ArrayList<DataRecipeDetail>>? = null
    var recipes: ArrayList<DataRecipeDetail> = arrayListOf()
    var token: String = ""


    fun getCollectionLiveData(): MutableLiveData<ArrayList<DataRecipeDetail>>? {
        if (recipes.isEmpty()){
            recipesLData = MutableLiveData()
            getCollection()
        }
        return recipesLData
    }

    fun getCollection() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getCollection(token).enqueue(object : Callback<Favorite> {
            override fun onResponse(call: Call<Favorite>, response: Response<Favorite>) {
                response.body()?.let { res ->
                    recipes = res.data
                    recipesLData?.postValue(recipes)
                }
            }

            override fun onFailure(call: Call<Favorite>, t: Throwable) {
                Log.d("fail", "" + t)
            }
        })

    }


}
