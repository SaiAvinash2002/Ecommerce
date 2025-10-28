package com.example.ecommerce.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartEntity(
    @PrimaryKey val product_id: String,
    val product_name: String,
    val description: String,
    val category_name: String,
    val subcategory_name: String,
    val price: String,
    val product_image_url: String,
    var quantity: Int = 1
)