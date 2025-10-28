package com.example.ecommerce.model.remote.dto


import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("bill_amount")
    val billAmount: Int,
    @SerializedName("delivery_address")
    val deliveryAddress: DeliveryAddress,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("user_id")
    val userId: Int
)