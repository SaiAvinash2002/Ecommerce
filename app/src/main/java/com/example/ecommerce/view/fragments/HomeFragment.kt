package com.example.ecommerce.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.view.CategoryAdapter
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.viewmodel.HomeViewModel

// This CartFragment uses HomeViewModel in MVVM

class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding
   private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadViewModel()
        setUpRecyclerView()
        loadCategories()
        homeViewModel.getCategories()
    }

    fun loadViewModel(){
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun setUpRecyclerView() {
        binding.rvContainer.layoutManager = GridLayoutManager(requireActivity(),2)
    }

    fun loadCategories(){
        homeViewModel.response.observe(viewLifecycleOwner){
            response ->
            binding.rvContainer.adapter = CategoryAdapter(response?.categories ?: emptyList()) { category ->
                openSubCategoryFragment(category.categoryId.toInt())
            }
        }
    }

    fun openSubCategoryFragment(categoryId: Int){
         when(categoryId){
             1-> {
                  parentFragmentManager.beginTransaction()
                      .replace(R.id.fragmentContainer, SmartPhonesFragment())
                      .commit()
             }
         }
    }
}

