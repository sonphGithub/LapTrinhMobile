package com.example.food01android.model.Home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val icon: String,
    val id: Int,
    val image: String,
    val name: String,
    val recipes: ArrayList<DataRecipeDetail>,
    val see_all: String
): Parcelable