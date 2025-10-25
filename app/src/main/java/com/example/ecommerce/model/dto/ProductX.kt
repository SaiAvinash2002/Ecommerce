package com.example.ecommerce.model.dto


import com.google.gson.annotations.SerializedName

data class ProductX(
    @SerializedName("average_rating")
    val averageRating: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("images")
    val images: List<ImageX>,
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
    val reviews: List<ReviewX>,
    @SerializedName("specifications")
    val specifications: List<SpecificationX>,
    @SerializedName("sub_category_id")
    val subCategoryId: String
)