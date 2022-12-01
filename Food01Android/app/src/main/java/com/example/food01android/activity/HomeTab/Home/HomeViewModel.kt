package com.example.food01android.activity.HomeTab.Home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Home.Category
import com.example.food01android.model.Home.DataX
import com.example.food01android.model.Home.Home
import com.example.food01android.model.Home.MoreCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {

    private var homeLiveData: MutableLiveData<DataX>? = null
    private var categoriesLiveData: MutableLiveData<ArrayList<Category>>? = null
    var categories: ArrayList<Category> = arrayListOf()
    var home: DataX = DataX()
    var token: String = ""
    var nextPageUrl: String = "http://food01.sboomtools.net:81/api/home/categories?page=2"


    fun getHomeLiveData(): MutableLiveData<DataX>? {
        if (home == DataX()) {
            homeLiveData = MutableLiveData()
            getHome()
        }
        return homeLiveData
    }

    fun loadMoreCate(): MutableLiveData<ArrayList<Category>>? {
        categoriesLiveData = MutableLiveData()
        return categoriesLiveData
    }

    private fun getHome() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getHome(token).enqueue(object : Callback<Home> {
            override fun onResponse(call: Call<Home>, response: Response<Home>) {
                response.body()?.let { res ->
                    home = res.data
                    categories = res.data.categories
                    homeLiveData?.postValue(home)
                }
            }

            override fun onFailure(call: Call<Home>, t: Throwable) {
                Log.e("Home", "error get Data" + t)
                homeLiveData?.postValue(DataX())
            }
        })
    }

    fun loadMore() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getMoreCate(token, nextPageUrl).enqueue(object : Callback<MoreCategory> {
            override fun onResponse(call: Call<MoreCategory>, response: Response<MoreCategory>) {
                if (nextPageUrl != "") {
                    response.body()?.let {
                        categories.addAll(it.data)
                        categoriesLiveData?.postValue(categories)
                        nextPageUrl = it.next_page_url
                        Log.e("next page", "" + nextPageUrl)
                    }
                }
            }

            override fun onFailure(call: Call<MoreCategory>, t: Throwable) {
                Log.d("Home", "error get more cate " + t)
                categoriesLiveData?.postValue(arrayListOf())
            }
        })

    }


}