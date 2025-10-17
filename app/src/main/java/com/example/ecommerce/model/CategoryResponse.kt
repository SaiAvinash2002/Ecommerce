package com.example.ecommerce.model


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)