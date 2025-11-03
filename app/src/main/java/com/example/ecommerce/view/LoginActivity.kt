package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.LoginPageBinding
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.view.DashboardActivity
import com.example.ecommerce.view.RegisterActivity
import com.example.ecommerce.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginPageBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.checkUser(email, password)
        }

        binding.tvNoAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                val sharedPreferences = getSharedPreferences("ecommerce", MODE_PRIVATE)
                sharedPreferences.edit {
                    putBoolean("isLoggedIn", true)
                }
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }
    }
}
