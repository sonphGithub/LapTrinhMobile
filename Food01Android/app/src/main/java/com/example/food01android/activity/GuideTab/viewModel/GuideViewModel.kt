package com.example.food01android.activity.GuideTab.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Guide.Guide
import com.example.food01android.model.Guide.GuideModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuideViewModel : ViewModel() {

    var token: String = ""
    var guideLData: MutableLiveData<ArrayList<Guide>>? = null
    var guides: ArrayList<Guide> = arrayListOf()

    fun getGuideLiveData(): MutableLiveData<ArrayList<Guide>>? {
        if (guides.isEmpty()) {
            guideLData = MutableLiveData()
            getGuideCategories()
        }
        return guideLData
    }

    fun getGuideCategories() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getGuide(token).enqueue(object : Callback<GuideModel> {
            override fun onResponse(call: Call<GuideModel>, response: Response<GuideModel>) {
                response.body()?.let { res ->
                    guides = res.data
                    guideLData?.postValue(guides)
                }
            }

            override fun onFailure(call: Call<GuideModel>, t: Throwable) {
                guideLData?.postValue(arrayListOf())
                Log.e("get api guide", "fail" + t)
            }
        })
    }
}