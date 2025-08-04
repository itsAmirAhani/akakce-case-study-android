package com.example.akakcecase.model

data class Rating(
    val rate: Float,
    val count: Int
)

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val rating: Rating
)
