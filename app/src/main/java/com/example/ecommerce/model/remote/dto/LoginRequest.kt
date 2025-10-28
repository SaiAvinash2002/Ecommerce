package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("password")
    val password: String
)