package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class Specification(
    @SerializedName("display_order")
    val displayOrder: String,
    @SerializedName("specification")
    val specification: String,
    @SerializedName("specification_id")
    val specificationId: String,
    @SerializedName("title")
    val title: String
)