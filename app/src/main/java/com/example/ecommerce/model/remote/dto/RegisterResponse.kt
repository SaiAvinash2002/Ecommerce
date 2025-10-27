package com.example.ecommerce.model.dto


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)