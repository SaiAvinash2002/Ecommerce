package com.example.ecommerce

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ecommerce.databinding.ActivityCheckOutBinding
import com.example.ecommerce.fragments.CartItemsFragment
import com.example.ecommerce.fragments.DeliveryFragment
import com.example.ecommerce.fragments.PaymentFragment
import com.example.ecommerce.fragments.SummaryFragment
import com.google.android.material.tabs.TabLayoutMediator

class CheckOutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckOutBinding
    private lateinit var viewpagerAdapter: CheckOutViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(binding.root)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        //create list of fragments
        val listOfFragments = listOf(CartItemsFragment(), DeliveryFragment(), PaymentFragment(),
            SummaryFragment())

        // initialize adapter
        viewpagerAdapter = CheckOutViewPagerAdapter(
            listOfFragments,
            supportFragmentManager,
            lifecycle
        )

        //set the adapter onto viewpager
        binding.vpContainer.adapter = viewpagerAdapter

        // attach tabLayout with viewpager and create tabs with text
        TabLayoutMediator(binding.tabLayout, binding.vpContainer) { tab, position ->
            tab.text = when (position) {
                0 -> "Cart Items"
                1 -> "Delivery"
                2 -> "Payment"
                3 -> "Summary"
                else -> ""
            }
        }.attach()
    }
}