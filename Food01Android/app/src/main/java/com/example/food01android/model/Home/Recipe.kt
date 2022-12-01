package com.example.food01android.model.Home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val image: String,
    val is_favorite: Int,
    val is_reviews: Int,
    val my_star: Int,
    val name: String
): Parcelable