package com.example.ecommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.model.local.AddressEntity
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.model.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeliveryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val apiService = ApiService.getInstance()

    val localAddresses: LiveData<List<AddressEntity>> = db.addressDao().getAddresses()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        // Starts observing when ViewModel is created
        observeLocalAddresses()
    }

    private fun observeLocalAddresses() {
        localAddresses.observeForever { addresses ->
            if (addresses.isEmpty()) {
                getRemoteAddresses()
            }
        }
    }

    private fun getRemoteAddresses() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            val response = apiService.getAddresssList(1)
            val remoteAddresses = response.body()?.addresses ?: emptyList()

            remoteAddresses.forEach { address ->
                db.addressDao().addAddress(
                    AddressEntity(
                        address_id = 0,
                        user_id = 1,
                        title = address.title,
                        address = address.address
                    )
                )
            }
            _isLoading.postValue(false)
        }
    }

    fun addAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.addressDao().addAddress(
                AddressEntity(
                    address_id = 0,
                    user_id = 1,
                    title = "Home",
                    address = address
                )
            )
        }
    }
}
