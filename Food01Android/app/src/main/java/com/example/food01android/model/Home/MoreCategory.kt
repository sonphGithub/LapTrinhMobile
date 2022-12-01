package com.example.food01android.model.Home

data class MoreCategory(
    val code: Int,
    val `data`: ArrayList<Category>,
    val message: String,
    val next_page_url: String
)
