package com.example.food01android.model.Home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val author: Author?,
    val content: String,
    val id: Int,
    val recipe_id: Int,
    val star: Int,
    val user_id: Int
): Parcelable