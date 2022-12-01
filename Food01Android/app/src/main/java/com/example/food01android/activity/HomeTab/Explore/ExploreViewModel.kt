package com.example.food01android.activity.HomeTab.Explore

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Favorite.Favorite
import com.example.food01android.model.Home.DataRecipeDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ExploreViewModel: ViewModel() {

    var categoryId: Int = 0
    var token: String = ""
    var exploreLData: MutableLiveData<ArrayList<DataRecipeDetail>>? = null
    var explores: ArrayList<DataRecipeDetail> = arrayListOf()

    fun getExploreLiveData(): MutableLiveData<ArrayList<DataRecipeDetail>>? {
        if(explores.isEmpty()){
            exploreLData = MutableLiveData()
            getExplore()
        }
        return exploreLData
    }

    fun getExplore() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getExplore(token, categoryId).enqueue(object : Callback<Favorite> {
            override fun onResponse(call: Call<Favorite>, response: Response<Favorite>) {
                response.body()?.let { res ->
                    explores = res.data
                    exploreLData?.postValue(explores)
                }
            }

            override fun onFailure(call: Call<Favorite>, t: Throwable) {
                Log.d("fail", "" + t)
            }
        })
    }
}