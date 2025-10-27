package com.example.ecommerce.model.dto


import com.example.ecommerce.model.dto.Subcategory
import com.google.gson.annotations.SerializedName

data class SubCategoryResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int,
    @SerializedName("subcategories")
    val subcategories: List<Subcategory>
)