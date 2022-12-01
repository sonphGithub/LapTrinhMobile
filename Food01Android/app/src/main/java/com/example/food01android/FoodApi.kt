package com.example.food01android
import com.example.food01android.model.Favorite.Favorite
import com.example.food01android.model.Guide.DetailArticle
import com.example.food01android.model.Guide.DetailGuideModel
import com.example.food01android.model.Guide.GuideModel
import com.example.food01android.model.Home.*
import com.example.food01android.model.Search.Search
import com.example.food01android.model.LogIn.LogIn
import com.example.food01android.model.Search.MoreSearch
import com.example.food01android.model.Settings.User
import okhttp3.OkHttpClient

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*





 interface FoodApi {

     companion object {

         operator fun invoke(): FoodApi {
             return Retrofit.Builder()
                 .baseUrl("http://food01.sboomtools.net:81/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build()
                 .create(FoodApi::class.java)
         }
     }

    @POST("api/user/login_facebook")
    fun logInFacebook(
        @Query("facebook_id") facebook_id: String,
        @Query("email") email: String
    ): Call<LogIn>

    @POST("api/user/login_google")
    fun logInGoogle(
        @Query("name") name: String,
        @Query("email") email: String
    ): Call<LogIn>

    @POST("api/user/skip_login")
    fun skipLogIn(@Query("uuid") uuid: String): Call<LogIn>

    @GET("api/home")
    fun getHome(@Header("Authorization") token: String): Call<Home>

    @GET("api/home/keywords_suggestion")
    fun getSuggestKeyword(@Header("Authorization") token: String): Call<Search>

    @GET("api/home/recipe")
    fun getRecipeDetail(
        @Header("Authorization") token: String,
        @Query("recipe_id") recipe_id: Int
    ): Call<RecipeDetail>

    @GET("api/home/recommendation")
    fun getRecipeRecommend(
        @Header("Authorization") token: String,
        @Query("recipe_id") recipe_id: Int
    ): Call<Recommend>

    @GET("api/user/get_user_info")
    fun getUserInfo(@Header("Authorization") token: String): Call<User>

    @GET("api/guide/categories")
    fun getGuide(@Header("Authorization") token: String): Call<GuideModel>

    @GET("api/guide/articles")
    fun getDetailGuide(
        @Header("Authorization") token: String,
        @Query("category_id") category_id: Int
    ): Call<DetailGuideModel>

    @GET("api/guide/article")
    fun getArticle(
        @Header("Authorization") token: String,
        @Query("article_id") article_id: Int
    ): Call<DetailArticle>

    @GET("api/favorite/community")
    fun getCommunity(@Header("Authorization") token: String): Call<Favorite>

    @GET("api/home/recipes_by_category")
    fun getExplore(
        @Header("Authorization") token: String,
        @Query("category_id") category_id: Int
    ): Call<Favorite>

    @GET("api/favorite/me")
    fun getCollection(@Header("Authorization") token: String): Call<Favorite>

    @POST("api/favorite/set_favorite")
    fun saveFavorite(@Query("id") id: Int,
                     @Header("Authorization") token: String): Call<GuideModel>

    @POST("api/favorite/remove_favorite")
    fun removeFavorite(
        @Query("id") id: Int,
        @Header("Authorization") token: String
    ): Call<GuideModel>

    @POST("api/user/set_vegan")
    fun setVegan(@Header("Authorization") token: String,
                 @Query("vegan") vegan: Int): Call<Home>

    @GET("api/home/search")
    fun getSearchResult(@Header("Authorization") token: String,
                        @Query("keyword") keyword: String): Call<Favorite>



     @GET("api/home/get_reviews")
     fun getReviewInRecipes(@Header("Authorization") token: String,
                            @Query("recipe_id") recipe_id: Int): Call<DataReview>

     @GET
     fun getMoreCate(@Header("Authorization") token: String, @Url url: String ): Call<MoreCategory>

     @GET
     fun getMoreSearch(@Header("Authorization") token: String, @Url url: String ): Call<MoreSearch>

     @POST("api/home/set_reviews")
     fun setReview(
         @Header("Authorization") token: String,
         @Query("recipe_id") recipe_id: Int,
         @Query("star") star: Int,
         @Query("content") content: String
     ): Call<DataReviewObject>

}