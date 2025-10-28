package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.RegisterPageBinding
import com.example.ecommerce.model.remote.ApiService
import com.example.ecommerce.showMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.model.remote.dto.RegisterRequest
import com.example.ecommerce.model.remote.dto.RegisterResponse
import com.example.ecommerce.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterPageBinding

    private lateinit var registerViewModel: RegisterViewModel
    val apiService: ApiService = ApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = RegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createRegisterViewModel()
        buttonClickListener()
        setUpObservers()
    }

    private fun createRegisterViewModel() {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
    }

    private fun buttonClickListener() {
        binding.btnRegister.setOnClickListener {
            addUser()
        }
    }

    fun addUser() {
        with(binding) {
            val name = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val mobile = etMobile.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // input validation
            registerViewModel.registerUser(name, email, mobile, password)
        }
    }

    fun setUpObservers() {
        registerViewModel.message.observe(this@RegisterActivity) {
            showMessage("Message", it)
            return@observe
        }

        registerViewModel.result.observe(this@RegisterActivity) { result ->
            val sharedPreferences = getSharedPreferences("ecommerce", MODE_PRIVATE)
            sharedPreferences.edit {
                putBoolean("isLoggedIn", true)
            }
        }
    }
}