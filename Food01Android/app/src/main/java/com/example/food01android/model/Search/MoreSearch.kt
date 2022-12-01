package com.example.food01android.model.Search

import com.example.food01android.model.Home.DataRecipeDetail

data class MoreSearch(
    val code: Int = 0,
    val `data`: ArrayList<DataRecipeDetail> = arrayListOf(),
    val message: String = "",
    val next_page_url: String = ""
)