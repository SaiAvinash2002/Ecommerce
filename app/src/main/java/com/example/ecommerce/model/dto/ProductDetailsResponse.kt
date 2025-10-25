package com.example.ecommerce.model.dto


import com.google.gson.annotations.SerializedName

data class ProductDetailsResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("product")
    val product: ProductX,
    @SerializedName("status")
    val status: Int
)