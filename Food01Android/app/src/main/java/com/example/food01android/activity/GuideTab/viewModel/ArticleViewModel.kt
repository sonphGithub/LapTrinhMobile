package com.example.food01android.activity.GuideTab.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food01android.FoodApi
import com.example.food01android.model.Guide.DataDetailArticle
import com.example.food01android.model.Guide.DetailArticle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel: ViewModel() {

    var token: String = ""
    var articleId: Int = 0
    var articleLData: MutableLiveData<DataDetailArticle>? = null
    var article: DataDetailArticle = DataDetailArticle()


    fun getArticleLiveData(): MutableLiveData<DataDetailArticle>? {
        if (article == DataDetailArticle()){
            articleLData = MutableLiveData()
            getArticle()
        }
        return articleLData
    }



    fun getArticle() {
        val foodApi: FoodApi = FoodApi.invoke()
        foodApi.getArticle(token, articleId).enqueue(object : Callback<DetailArticle> {
            override fun onResponse(call: Call<DetailArticle>, response: Response<DetailArticle>) {
                response.body()?.let { res ->
                    article = res.data
                    articleLData?.postValue(article)
                }
            }

            override fun onFailure(call: Call<DetailArticle>, t: Throwable) {
                Log.d("get article fail", "" + t)
            }
        })
    }

}