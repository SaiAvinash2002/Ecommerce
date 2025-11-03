package com.example.ecommerce.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.R
import com.example.ecommerce.view.fragments.CartFragment
import com.example.ecommerce.view.fragments.HomeFragment
import com.example.ecommerce.view.fragments.OrdersFragment
import com.example.ecommerce.view.fragments.ProfileFragment

class DashBoardViewModel: ViewModel() {
    private val _currentFragment = MutableLiveData<Fragment>()
    val currentFragment: LiveData<Fragment> get() = _currentFragment
    private val _toolbarTitle = MutableLiveData<String>()
    val toolbarTitle: LiveData<String> get() = _toolbarTitle

    fun selectFragment(menuId: Int) {
        when (menuId) {
            R.id.homeMenu -> {
                _currentFragment.value = HomeFragment()
                _toolbarTitle.value = "Home"
            }

            R.id.cartMenu -> {
                _currentFragment.value = CartFragment()
                _toolbarTitle.value = "Cart"
            }

            R.id.ordersMenu -> {
                _currentFragment.value = OrdersFragment()
                _toolbarTitle.value = "Orders"
            }

            R.id.profileMenu -> {
                _currentFragment.value = ProfileFragment()
                _toolbarTitle.value = "Profile"
            }
        }
    }
    fun setDefaultFragment() {
        _currentFragment.value = HomeFragment()
        _toolbarTitle.value = "Home"
    }
}