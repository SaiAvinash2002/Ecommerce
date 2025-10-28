package com.example.ecommerce.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.remote.ApiService
import com.example.ecommerce.model.remote.dto.CategoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val apiService: ApiService = ApiService.getInstance()
    private var _response = MutableLiveData<CategoryResponse?>()
    val response: LiveData<CategoryResponse?> = _response
    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = apiService.getCategories()
                if(!data.isSuccessful) {
                    Log.d("Error", "Failed to process request.")
                    return@launch
                }
                val result = data.body()
                if (result != null) {
                    _response.postValue(result)
                }
            }
            catch (e: Exception){
                Log.d("HomeViewModel","Unable to process the request")
            }
        }
    }
}