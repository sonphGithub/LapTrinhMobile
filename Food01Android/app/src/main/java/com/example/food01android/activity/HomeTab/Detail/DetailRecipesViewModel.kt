package com.example.food01android.activity.HomeTab.Detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Home.Recommend
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRecipesViewModel : ViewModel() {

    var recipeId: Int = 0
    var token: String = ""
    var recommendLiveData: MutableLiveData<ArrayList<DataRecipeDetail>>? = null
    var recommends: ArrayList<DataRecipeDetail> = arrayListOf()


    fun getRecommendLData(): MutableLiveData<ArrayList<DataRecipeDetail>>?{
        if (recommends.isEmpty()){
            recommendLiveData = MutableLiveData()
            getRecommend()
        }
        return recommendLiveData
    }

    fun getRecommend() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getRecipeRecommend(token, recipeId).enqueue(object : Callback<Recommend> {
            override fun onResponse(call: Call<Recommend>, response: Response<Recommend>) {
                response.body()?.let { res ->
                    recommends = res.data
                    recommendLiveData?.postValue(recommends)
                }

            }

            override fun onFailure(call: Call<Recommend>, t: Throwable) {
                Log.d("fail", "" + t)
            }
        })
    }
}