package com.example.food01android.model.Home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDetail(
    val code: Int,
    val `data`: DataRecipeDetail = DataRecipeDetail(),
    val message: String
    ): Parcelable