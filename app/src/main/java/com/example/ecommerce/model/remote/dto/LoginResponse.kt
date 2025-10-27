package com.example.ecommerce.model.dto


import com.example.ecommerce.model.dto.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("user")
    val user: User
)