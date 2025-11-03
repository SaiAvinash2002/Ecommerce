package com.example.ecommerce.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.model.remote.ApiService
import com.example.ecommerce.model.remote.dto.SubCategoryProductsListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmartPhonesViewModel(application: Application) : AndroidViewModel(application) {
    private val apiService = ApiService.getInstance()
    val db = AppDatabase.getDatabase(application.applicationContext)

    private val _result = MutableLiveData<SubCategoryProductsListResponse>()
    val result: LiveData<SubCategoryProductsListResponse> = _result

    private val _navigateToProduct = MutableLiveData<Int?>()
    val navigateToProduct: LiveData<Int?> = _navigateToProduct

    fun getSubCategoryProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getSubCategoryProductsList(1)
                if (response.isSuccessful) {
                    _result.postValue(response.body())
                }
            } catch (e: Exception) {
                Log.d("SmartPhonesViewModel", "Failed: ${e.message}")
            }
        }
    }

    val itemClickListener = object : ItemClickListener {
        override fun onClick(id: Int) {
            _navigateToProduct.value = id
        }
    }

    fun doneNavigating() {
        _navigateToProduct.value = null
    }
}

interface ItemClickListener {
    fun onClick(id: Int)
}