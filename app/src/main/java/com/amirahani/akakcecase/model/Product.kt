package com.example.akakcecase.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Rating(
    val rate: Float,
    val count: Int
) : Parcelable

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val description: String,
    val rating: Rating
) : Parcelable
