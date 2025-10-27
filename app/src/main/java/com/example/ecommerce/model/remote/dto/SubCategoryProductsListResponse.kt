package com.example.ecommerce.model.dto

import com.example.ecommerce.Product
import com.google.gson.annotations.SerializedName

data class SubCategoryProductsListResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("products")
    val products: List<Product>,
    @SerializedName("status")
    val status: Int
)