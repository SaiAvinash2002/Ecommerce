package com.example.ecommerce.model.dto


import com.google.gson.annotations.SerializedName

data class Addresse(
    @SerializedName("address")
    val address: String,
    @SerializedName("address_id")
    val addressId: String,
    @SerializedName("title")
    val title: String
)