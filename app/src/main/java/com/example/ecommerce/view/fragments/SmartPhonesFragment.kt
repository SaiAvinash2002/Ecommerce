package com.example.ecommerce.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentSmartPhonesBinding
import com.example.ecommerce.view.fragments.ProductDetailsFragment
import com.example.ecommerce.view.SubCategoryProductsListAdapter
import com.example.ecommerce.viewmodel.SmartPhonesViewModel

// This SmartPhonesFragment uses SmartPhonesViewModel in MVVM

class SmartPhonesFragment : Fragment() {

    private lateinit var binding: FragmentSmartPhonesBinding
    private lateinit var smartPhonesViewModel: SmartPhonesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmartPhonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createViewModel()
        setRecyclerview()
        updateDbOnClickCart()
        navigateToProductDetailsPage()
    }
    private fun createViewModel() {
        smartPhonesViewModel = ViewModelProvider(this)[SmartPhonesViewModel::class.java]
        smartPhonesViewModel.getSubCategoryProducts()
    }

    private fun setRecyclerview() {
        binding.rvContainer.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun updateDbOnClickCart() {
        smartPhonesViewModel.result.observe(viewLifecycleOwner) { data ->
            binding.rvContainer.adapter = SubCategoryProductsListAdapter(
                data?.products ?: emptyList(),
                smartPhonesViewModel.db,
                viewLifecycleOwner.lifecycleScope,
                smartPhonesViewModel.itemClickListener
            )
        }
    }
    private fun navigateToProductDetailsPage() {
        smartPhonesViewModel.navigateToProduct.observe(viewLifecycleOwner) { id ->
            id?.let {
                val fragment = ProductDetailsFragment()
                fragment.arguments = Bundle().apply { putInt("id", it) }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
                smartPhonesViewModel.doneNavigating()
            }
        }
    }
}
