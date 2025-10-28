package com.example.ecommerce.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.R
import com.example.ecommerce.databinding.DashboardPageBinding
import com.example.ecommerce.view.fragments.SearchFragment
import com.example.ecommerce.viewmodel.DashBoardViewModel

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: DashboardPageBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var viewModel: DashBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DashboardPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        setSupportActionBar(binding.toolbar)
//      Drawer Toggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

//        Initializing viewModel
        viewModel = ViewModelProvider(this)[DashBoardViewModel::class.java]

        viewModel.currentFragment.observe(this) { fragment ->
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit()
        }

        viewModel.toolbarTitle.observe(this) { title ->
            binding.toolbar.title = title
        }
//        Default Fragment
        if (savedInstanceState == null) {
            viewModel.setDefaultFragment()
            binding.navView.setCheckedItem(R.id.homeMenu)
        }
//      Handling the click on side drawer items
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            viewModel.selectFragment(menuItem.itemId)
            true
        }
        searchProducts()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun searchProducts() {
        binding.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2
//                Checks if the click is on the search icon by calculating total width - width of edit text except search icon.
                if (event.rawX >= (binding.searchEditText.right -
                            binding.searchEditText.compoundDrawables[drawableEnd].bounds.width())
                ) {
//                 The user tapped the search icon
                    val query = binding.searchEditText.text.toString()
//                    /******* TO BE NAVIGATE FOR SEARCH FRAGMENT *******/
                    val fragment = SearchFragment()
                    val bundle = Bundle()   // Send the search query to the Search Fragment
                    bundle.putString("searchQuery", query)
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .commit()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }
}