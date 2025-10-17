package com.example.ecommerce

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.ecommerce.databinding.DashboardPageBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: DashboardPageBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DashboardPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        // Drawer Toggle Setup
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

//        Default Fragment
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
            binding.navView.setCheckedItem(R.id.homeMenu)
        }
//
        binding.navView.setNavigationItemSelectedListener {
            menuItem ->
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)

            when(menuItem.itemId){
                R.id.homeMenu -> replaceFragment( HomeFragment(),"Home")
                R.id.cartMenu ->  replaceFragment(CartFragment(),"Cart")
                R.id.ordersMenu ->  replaceFragment(OrdersFragment(),"Orders")
                R.id.profileMenu ->  replaceFragment(ProfileFragment(),"Profile")
            }
            true
        }
    }

    fun replaceFragment(fragment: androidx.fragment.app.Fragment,title: String){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
        binding.toolbar.title = title
    }
}