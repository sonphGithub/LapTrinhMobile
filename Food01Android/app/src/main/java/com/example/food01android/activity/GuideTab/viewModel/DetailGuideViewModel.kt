package com.example.food01android.activity.GuideTab.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Guide.DetailGuideData
import com.example.food01android.model.Guide.DetailGuideModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailGuideViewModel : ViewModel() {

    var token: String = ""
    var categoryId: Int = 0

    var detailGuidesLData: MutableLiveData<ArrayList<DetailGuideData>>? = null
    var detailGuides: ArrayList<DetailGuideData> = arrayListOf()

    fun getDetailGuideLiveData(): MutableLiveData<ArrayList<DetailGuideData>>? {
        if (detailGuides.isEmpty()){
            detailGuidesLData = MutableLiveData()
            getDetailGuide()
        }
        return detailGuidesLData
    }

    fun getDetailGuide() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getDetailGuide(token, categoryId).enqueue(object : Callback<DetailGuideModel> {
            override fun onResponse(call: Call<DetailGuideModel>, response: Response<DetailGuideModel>) {
                response.body()?.let { res ->
                    detailGuides = res.data
                    detailGuidesLData?.postValue(detailGuides)
                }
            }

            override fun onFailure(call: Call<DetailGuideModel>, t: Throwable) {
                detailGuidesLData?.postValue(arrayListOf())
                Log.e("get api detail guide", "fail" + t)
            }
        })
    }
}