package com.example.food01android.model.Home

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataRecipeDetail(
    @PrimaryKey
    var id: Int? = null,
    var about: String? = null,
    var image: String? = null ,
    var ingredients_list: ArrayList<Ingredients> = arrayListOf(),
    var is_favorite: Int = 0,
    var is_reviews: Int = 0,
    var methods_list: ArrayList<Methods> = arrayListOf(),
    var my_star: Int = 0,
    var name: String? = null,
    var nutritions_list: ArrayList<Ingredients> = arrayListOf(),
    var rate: Int = 0,
    var result: String? = null,
    var reviews: ArrayList<Review> = arrayListOf(),
    var score: Int = 0,
    var star: Double = 0.0,
    var tags: String? = null,
    var total_reviews: Int = 0,
    var total_time: String? = null,
    var video: String? = null
): Parcelable