package com.example.archaeological_fieldwork.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var id: Long = 0,
                         var name: String = "",
                         var description: String = "",
                         var visited: Boolean = false,
                         var image: String = ""
                            ) : Parcelable

