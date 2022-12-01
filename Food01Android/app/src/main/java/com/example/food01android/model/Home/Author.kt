package com.example.food01android.model.Home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val avatar: String,
    val facebook_id: String?,
    val id: Int,
    val name: String
): Parcelable