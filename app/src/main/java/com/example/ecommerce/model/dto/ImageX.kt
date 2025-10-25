package com.example.ecommerce.model.dto


import com.google.gson.annotations.SerializedName

data class ImageX(
    @SerializedName("display_order")
    val displayOrder: String,
    @SerializedName("image")
    val image: String
)