package com.example.food01android.model.Guide

data class DetailGuideModel(
    val code: Int = 0,
    val message: String = "",
    val data: ArrayList<DetailGuideData> = arrayListOf()
)
