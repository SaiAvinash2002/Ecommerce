package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.model.local.CartEntity
import com.example.ecommerce.view.fragments.CartItemsFragment
import com.example.ecommerce.view.fragments.DeliveryFragment
import com.example.ecommerce.view.fragments.PaymentFragment
import com.example.ecommerce.view.fragments.SummaryFragment
import com.example.ecommerce.model.local.CartDao

class CheckOutViewModel(private val cartDao: CartDao) : ViewModel() {
    val cartItems: LiveData<List<CartEntity>> = cartDao.getAllItems()
    val address = MutableLiveData<String>()

    val listOfFragments = listOf(
        CartItemsFragment(),
        DeliveryFragment(),
        PaymentFragment(),
        SummaryFragment()
    )

    val tabTitles = listOf("Cart Items", "Delivery", "Payment", "Summary")

}
