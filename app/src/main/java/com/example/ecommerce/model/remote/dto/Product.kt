package com.example.ecommerce.model.remote.dto

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("average_rating")
    val averageRating: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("is_active")
    val isActive: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_image_url")
    val productImageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("reviews")
    val reviews: List<Review>,
    @SerializedName("specifications")
    val specifications: List<Specification>,
    @SerializedName("sub_category_id")
    val subCategoryId: String
)