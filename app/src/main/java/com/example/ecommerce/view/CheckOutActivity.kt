package com.example.ecommerce.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.CheckOutViewModelFactory
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.databinding.ActivityCheckOutBinding
import com.example.ecommerce.viewmodel.CheckOutViewModel
import com.google.android.material.tabs.TabLayoutMediator

class CheckOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckOutBinding
    private lateinit var checkOutViewModel: CheckOutViewModel
    private lateinit var viewpagerAdapter: CheckOutViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // Initialize database and ViewModel with factory
        val database = AppDatabase.getDatabase(this)
        val viewModelFactory = CheckOutViewModelFactory(database.cartDao())
        checkOutViewModel = ViewModelProvider(this, viewModelFactory)
            .get(CheckOutViewModel::class.java)

        setUpViewPager()
    }

    private fun setUpViewPager() {
        viewpagerAdapter = CheckOutViewPagerAdapter(
            checkOutViewModel.listOfFragments,
            supportFragmentManager,
            lifecycle
        )

        binding.vpContainer.adapter = viewpagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.vpContainer) { tab, position ->
            tab.text = checkOutViewModel.tabTitles[position]
        }.attach()
    }
}
