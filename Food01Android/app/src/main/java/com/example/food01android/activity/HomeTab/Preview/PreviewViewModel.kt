package com.example.food01android.activity.HomeTab.Preview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Home.RecipeDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreviewViewModel : ViewModel() {

    var token: String = ""
    var recipeId: Int = 0
    var recipeLiveData: MutableLiveData<DataRecipeDetail>? = null
    var recipe: DataRecipeDetail = DataRecipeDetail()

    fun getDetailLiveData(): MutableLiveData<DataRecipeDetail>?{
        if (recipe == DataRecipeDetail()){
            recipeLiveData = MutableLiveData()
            getDetail()
        }
        return recipeLiveData
    }

    fun getDetail() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getRecipeDetail(token, recipeId).enqueue(object : Callback<RecipeDetail> {
            override fun onResponse(call: Call<RecipeDetail>, response: Response<RecipeDetail>) {
                response.body()?.let { res ->
                    recipe = res.data
                    recipeLiveData?.postValue(recipe)
                }
            }

            override fun onFailure(call: Call<RecipeDetail>, t: Throwable) {
                Log.d("fail", "getPreview" + t)
                recipeLiveData?.value = DataRecipeDetail()
            }
        })
    }


}