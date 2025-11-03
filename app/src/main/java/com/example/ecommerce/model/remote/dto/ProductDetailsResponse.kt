package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("product")
    val product: Product,
    @SerializedName("status")
    val status: Int
)