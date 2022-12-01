package com.example.food01android.model.Home

data class DataReview(
    val code: Int,
    val `data`: List<Review>,
    val message: String,
    val next_page_url: String
)

data class DataReviewObject(
    val code: Int,
    val `data`: Review,
    val message: String,
    val next_page_url: String
)
