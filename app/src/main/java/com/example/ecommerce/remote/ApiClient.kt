package com.example.ecommerce.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//
object ApiClient {
    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor (interceptor)
        .build()

    // to send request  from emulator use 10.0.2.2
    // to send request  from your device ip-address-of-laptop use ip-config in cmd
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2/myshop/index.php/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}