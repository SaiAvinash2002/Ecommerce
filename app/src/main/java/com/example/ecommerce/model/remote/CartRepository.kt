package com.example.ecommerce.model.remote

import androidx.lifecycle.LiveData
import com.example.ecommerce.model.local.CartDao
import com.example.ecommerce.model.local.CartEntity

class CartRepository(private val cartDao: CartDao) {
    val getAllItems: LiveData<List<CartEntity>> = cartDao.getAllItems()

}