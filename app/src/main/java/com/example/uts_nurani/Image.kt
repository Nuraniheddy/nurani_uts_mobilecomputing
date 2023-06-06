package com.example.uts_nurani

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image (
    val imageSrc:Int,
    val imageTitle:String,
    val imageDesc:String,
): Parcelable