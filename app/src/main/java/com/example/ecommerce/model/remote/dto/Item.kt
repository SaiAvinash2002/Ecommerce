package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int,
    @SerializedName("unit_price")
    val unitPrice: Int
)