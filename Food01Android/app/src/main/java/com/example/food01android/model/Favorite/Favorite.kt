package com.example.food01android.model.Favorite

import android.os.Parcelable
import com.example.food01android.model.Home.DataRecipeDetail
import com.example.food01android.model.Home.Review
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    val code: Int = 0,
    val data: ArrayList<DataRecipeDetail> = arrayListOf(),
    val dataReview: ArrayList<Review> = arrayListOf(),
    val message: String = "",
    val next_page_url: String = ""
): Parcelable

