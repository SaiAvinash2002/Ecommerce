package com.example.ecommerce.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.model.remote.LoginRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = LoginRepository()
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun checkUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Please fill all fields"
            return
        }

        viewModelScope.launch {
             try {
                    val response = repository.checkLogin(email, password)
                    if (response.isSuccessful && response.body() != null) {
                        val result = response.body()!!
                        if (result.status == 0) {
                            _loginSuccess.value = true
                        } else {
                            _errorMessage.value = result.message
                            Log.d("LoginCheck", "Login failed: ${result.message}")
                        }
                    } else {
                        _errorMessage.value = "Server error. Try again later."
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Network error: ${e.message}"
                    Log.e("LoginCheck", "Exception: ${e.message}", e)
                }
            }
        }

    }
