package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.LoginPageBinding
import com.example.ecommerce.model.LoginRequest
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginPageBinding
    val apiService: ApiService = ApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            checkUser()
        }

        binding.tvNoAccount.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    fun checkUser(){
        with(binding) {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

//            Validating Input fields
            if (email.isEmpty() || password.isEmpty()){
                showMessage("Validation Error", "Please fill all fields.")
                return
            }

            val checkLoginRequest = LoginRequest( email, password)

            val call: Call<LoginResponse> = apiService.checkUser(checkLoginRequest)

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse( call: Call<LoginResponse?>,  response: Response<LoginResponse?>) {
                    if(!response.isSuccessful) {
                        showMessage("Error", "Failed to process request. Error is: ${response.errorBody()}")
                        return
                    }

                    val result = response.body()

                    if (result != null) {
//                      Save the device as LoggedIn
                        val sharedPreferences = getSharedPreferences("ecommerce", MODE_PRIVATE)
                        sharedPreferences.edit {
                            putString("email",email)
                            putString("password",password)
                            putBoolean("isLoggedIn", true)
                        }
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    showMessage("Error", "Failed to save Register. Please retry again.")
                }
            })
        }
    }
}