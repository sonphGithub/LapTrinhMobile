package com.example.food01android.activity.FavoriteTab.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.activity.FavoriteTab.FavoriteMode

class FavoriteViewModel : ViewModel() {
    var isFavoriteMode : MutableLiveData<FavoriteMode> = MutableLiveData(FavoriteMode.Collection)
    var token: String = ""


//    fun getMyFav() {
//        val foodApi: FoodApi = FoodApi.invoke()
//        foodApi.getMyFav(token).enqueue(object : Callback<Favorite> {
//            override fun onResponse(call: Call<Favorite>, response: Response<Favorite>) {
//                for (i in response.body()?.data!!) {
//                    recipes.addAll(listOf(i))
//                    adapterRecyclerViewFavorite.notifyDataSetChanged()
//                }
//            }
//
//            override fun onFailure(call: Call<Favorite>, t: Throwable) {
//                Log.d("fail", "" + t)
//            }
//        })
//    }
//
//    fun getFavCommunity() {
//        val foodApi: FoodApi = FoodApi.invoke()
//        foodApi.getFavCommunity(token).enqueue(object : Callback<Favorite> {
//            override fun onResponse(call: Call<Favorite>, response: Response<Favorite>) {
//                Log.d("success", "getFav" + response.body()?.data!!)
//                for (i in response.body()?.data!!) {
//                    recipes.addAll(listOf(i))
//                    adapterRecyclerViewFavorite.notifyDataSetChanged()
//                }
//            }
//            override fun onFailure(call: Call<Favorite>, t: Throwable) {
//                Log.d("fail", "" + t)
//            }
//        })
//
//    }



}
