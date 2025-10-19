package com.example.ecommerce.remote

import com.example.ecommerce.SubCategoryProductsListResponse
import com.example.ecommerce.model.CategoryResponse
import com.example.ecommerce.model.LoginRequest
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.RegisterRequest
import com.example.ecommerce.model.RegisterResponse
import com.example.ecommerce.model.SubCategoryResponse
import retrofit2.Call
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
    fun checkUser(
        @Body checkUserRequest: LoginRequest
    ): Call<LoginResponse>

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

    companion object {
        fun getInstance() = ApiClient.retrofit.create(ApiService::class.java)
    }
}