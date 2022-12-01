package com.example.food01android.model.Home

import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
open class Ingredients(
    @PrimaryKey
    var id: Int? = null,
    var name: String = ""
): Parcelable, RealmObject()