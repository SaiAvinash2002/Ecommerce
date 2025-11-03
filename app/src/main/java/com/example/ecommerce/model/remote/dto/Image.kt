package com.example.ecommerce.model.remote.dto

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("display_order")
    val displayOrder: String,
    @SerializedName("image")
    val image: String
)