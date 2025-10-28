package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class SearchProductResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("status")
    val status: Int
)