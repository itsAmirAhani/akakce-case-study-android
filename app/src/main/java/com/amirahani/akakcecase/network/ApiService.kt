package com.example.akakcecase.network

import com.example.akakcecase.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getAllProducts(): List<Product>

    @GET("products")
    suspend fun getLimitedProducts(@Query("limit") limit: Int): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}
