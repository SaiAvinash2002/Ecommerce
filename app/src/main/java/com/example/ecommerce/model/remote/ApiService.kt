package com.example.ecommerce.model.remote

import com.example.ecommerce.model.remote.dto.AddressResponse
import com.example.ecommerce.model.remote.dto.CategoryResponse
import com.example.ecommerce.model.remote.dto.LoginRequest
import com.example.ecommerce.model.remote.dto.LoginResponse
import com.example.ecommerce.model.remote.dto.OrderRequest
import com.example.ecommerce.model.remote.dto.ProductDetails
import com.example.ecommerce.model.remote.dto.RegisterRequest
import com.example.ecommerce.model.remote.dto.RegisterResponse
import com.example.ecommerce.model.remote.dto.SearchProductResponse
import com.example.ecommerce.model.remote.dto.SubCategoryProductsListResponse
import com.example.ecommerce.model.remote.dto.SubCategoryResponse
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
    suspend fun addUser(
        @Body addUserRequest: RegisterRequest
    ): Response<RegisterResponse>

    @Headers("Content-type: application/json")
    @POST("User/auth")
    suspend fun checkUser(
        @Body checkUserRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("Category")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("SubCategory")
    fun getSubCategories(
        @Query("category_id") categoryId: Int
    ):Call<SubCategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    suspend fun getSubCategoryProductsList(
        @Path("sub_category_id") subCategoryId: Int
    ): Response<SubCategoryProductsListResponse>

    @GET("Product/details/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") productId: Int
    ): Response<ProductDetails>

    @GET("User/addresses/{user_id}")
    suspend fun getAddresssList(
        @Path("user_id") userId: Int
    ): Response<AddressResponse>

    @GET("Product/search")
    suspend fun getSearchProducts(
        @Query("query") query: String
    ): Response<SearchProductResponse>

    @POST("/Order")
    suspend fun addOrder(
        @Body order: OrderRequest
    )

    companion object {
        fun getInstance() = ApiClient.retrofit.create(ApiService::class.java)
    }
}