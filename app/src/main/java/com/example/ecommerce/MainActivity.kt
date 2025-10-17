package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.LoginPageBinding
import com.example.ecommerce.remote.ApiService

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginPageBinding
    val apiService: ApiService = ApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkedHasLogin()
    }

    fun checkedHasLogin(){
        val sharedPreferences = getSharedPreferences("ecommerce",MODE_PRIVATE)
        if (sharedPreferences.getBoolean("isLoggedIn",false)){
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
        else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}