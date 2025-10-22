package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.RegisterPageBinding
import com.example.ecommerce.model.RegisterRequest
import com.example.ecommerce.model.RegisterResponse
import com.example.ecommerce.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterPageBinding
    val apiService: ApiService = ApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = RegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            addUser()
        }
    }

    fun addUser(){
        with(binding) {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val mobile = etMobile.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Simple input validation
            if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
                showMessage("Validation Error", "Please fill all fields.")
                return
            }

            val addUserRequest = RegisterRequest(email, name, mobile, password)

            val call: Call<RegisterResponse> = apiService.addUser(addUserRequest)

            call.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse?>,
                    response: Response<RegisterResponse?>
                ) {
                    if(!response.isSuccessful) {
                        showMessage("Error", "Failed to process request. Error is: ${response.errorBody()}")
                        return
                    }

                    val result = response.body()

                    if(result != null) {
                        startActivity(Intent(this@RegisterActivity, DashboardActivity::class.java))
                        return
                    }
                    showMessage("Error", "Empty response from server. Please try again.")
                }

                override fun onFailure(
                    call: Call<RegisterResponse?>,
                    t: Throwable
                ) {
                    showMessage("Error", "Failed to save Register. Please retry again.")
                }
            })
        }
    }
}