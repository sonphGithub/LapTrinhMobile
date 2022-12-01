package com.example.food01android.model.Guide

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailGuideData(
    val id: Int = 0,
    val image: String = "",
    val title: String = "",
    val part: Int = 0,
    val parts: ArrayList<DetailGuideData> = arrayListOf()
): Parcelable
