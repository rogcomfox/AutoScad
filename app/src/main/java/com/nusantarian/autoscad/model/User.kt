package com.nusantarian.autoscad.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        val email: String,
        val name: String,
        val phone: String,
        val imageURL: String
) : Parcelable