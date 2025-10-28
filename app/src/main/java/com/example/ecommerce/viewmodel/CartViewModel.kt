package com.example.ecommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ecommerce.model.remote.CartRepository
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.model.local.CartEntity

class CartViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var  cartRepository : CartRepository
    var  cartItems : LiveData<List<CartEntity>>
    var dao: AppDatabase

    init {
        dao = AppDatabase.Companion.getDatabase(application)
        val cartDb = dao.cartDao()
        cartRepository = CartRepository(cartDb)
        cartItems = cartRepository.getAllItems
    }
}