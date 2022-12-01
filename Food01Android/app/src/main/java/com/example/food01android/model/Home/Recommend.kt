package com.example.food01android.model.Home

data class Recommend(
    val code: Int,
    val `data`: ArrayList<DataRecipeDetail>,
    val message: String
)