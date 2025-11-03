package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("addresses")
    val addresses: List<Addresse>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)