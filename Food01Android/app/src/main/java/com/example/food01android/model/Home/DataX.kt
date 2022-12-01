package com.example.food01android.model.Home

data class DataX(
    val categories: ArrayList<Category> = arrayListOf(),
    val explore: ArrayList<Explore> = arrayListOf(),
    val top: ArrayList<Top> = arrayListOf(),
    val trending: ArrayList<DataRecipeDetail> = arrayListOf()
)