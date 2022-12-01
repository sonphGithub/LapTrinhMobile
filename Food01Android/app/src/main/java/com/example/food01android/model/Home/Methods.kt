package com.example.food01android.model.Home

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Methods(
    val image: String = "",
    val name: String = ""
): Parcelable