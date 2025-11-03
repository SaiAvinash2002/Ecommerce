package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class DeliveryAddress(
    @SerializedName("address")
    val address: String,
    @SerializedName("title")
    val title: String
)