package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.remote.ApiService
import com.example.ecommerce.model.remote.dto.RegisterRequest
import com.example.ecommerce.model.remote.dto.RegisterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {
    private val _result = MutableLiveData<RegisterResponse?>()
    val result : LiveData<RegisterResponse?> = _result
    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message
    val apiService: ApiService = ApiService.getInstance()

    fun registerUser(name:String, email:String, mobile:String, password:String){
        // input validation
        if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
            _message.value = "Please fill all fields."
            return
        }
        val addUserRequest = RegisterRequest(email, name, mobile, password)
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = apiService.addUser(addUserRequest)

                if (!response.isSuccessful) {
                    _message.value = "Failed to process request. Error is: ${response.errorBody()}"
                    return@launch
                }
                val result = response.body()
                if (result != null) {
                    _result.value = result
                    _message.value = "Registriation Successful"
                } else {
                    _message.value = "Empty response from server. Please try again."
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}