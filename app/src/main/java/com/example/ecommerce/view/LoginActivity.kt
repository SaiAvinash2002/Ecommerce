package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.content.edit
import com.example.ecommerce.databinding.LoginPageBinding
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

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[LoginViewModel::class.java]

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                saveLoginState()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            showMessage("Error", error)
        }
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.checkUser(email, password)
        }

        binding.tvNoAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun saveLoginState() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val sharedPreferences = getSharedPreferences("ecommerce", MODE_PRIVATE)
        sharedPreferences.edit {
            putString("email", email)
            putString("password", password)
            putBoolean("isLoggedIn", true)
        }
    }

    private fun showMessage(title: String, message: String) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}
