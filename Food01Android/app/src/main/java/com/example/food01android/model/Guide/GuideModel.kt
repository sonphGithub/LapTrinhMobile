package com.example.food01android.model.Guide



data class GuideModel(
    val code: Int = 0,
    val message: String = "",
    val data: ArrayList<Guide> = arrayListOf()
)