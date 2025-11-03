package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("mobile_no")
    val mobileNo: String,
    @SerializedName("password")
    val password: String
)