package com.example.ecommerce.model.remote

import com.example.ecommerce.model.remote.dto.LoginRequest
import com.example.ecommerce.model.remote.dto.LoginResponse
import retrofit2.Response

class LoginRepository(private val apiService: ApiService = ApiService.Companion.getInstance()) {

    suspend fun checkLogin(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return apiService.checkUser(request)
    }
}