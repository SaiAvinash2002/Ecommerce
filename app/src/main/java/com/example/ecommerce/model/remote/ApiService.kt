package com.example.ecommerce.remote

import com.example.ecommerce.model.dto.AddressResponse
import com.example.ecommerce.model.dto.SubCategoryProductsListResponse
import com.example.ecommerce.model.dto.CategoryResponse
import com.example.ecommerce.model.dto.LoginRequest
import com.example.ecommerce.model.dto.LoginResponse
import com.example.ecommerce.model.dto.Product
import com.example.ecommerce.model.dto.ProductDetails
import com.example.ecommerce.model.dto.RegisterRequest
import com.example.ecommerce.model.dto.RegisterResponse
import com.example.ecommerce.model.dto.SubCategoryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //    saying that the request sending with JSON payload.
    @Headers("Content-type: application/json")
    @POST("User/register")
    fun addUser(
        @Body addUserRequest: RegisterRequest
    ): Call<RegisterResponse>

    @Headers("Content-type: application/json")
    @POST("User/auth")
    suspend fun checkUser(
        @Body checkUserRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("Category")
    fun getCategories(): Call<CategoryResponse>

    @GET("SubCategory")
    fun getSubCategories(
        @Query("category_id") categoryId: Int
    ):Call<SubCategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    fun getSubCategoryProductsList(
        @Path("sub_category_id") subCategoryId: Int
    ): Call<SubCategoryProductsListResponse>

    @GET("Product/details/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") productId: Int
    ): Response<ProductDetails>

    @GET("User/addresses/{user_id}")
    suspend fun getAddresssList(
        @Path("user_id") userId: Int
    ): Response<AddressResponse>
    companion object {
        fun getInstance() = ApiClient.retrofit.create(ApiService::class.java)
    }
}