package com.example.ecommerce.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.LoginPageBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: LoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = LoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
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