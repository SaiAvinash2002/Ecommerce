package com.example.ecommerce.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.Product
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentSearchBinding
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.model.remote.ApiService
import com.example.ecommerce.view.fragments.ProductDetailsFragment
import com.example.ecommerce.view.SubCategoryProductsListAdapter
import com.example.ecommerce.viewmodel.ItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchProducts()
    }
    val itemClickListener = object : ItemClickListener {
        override fun onClick(id: Int) {
            val fragment = ProductDetailsFragment()
//          Use bundles to send id to ProductDetailsFragment for fetching data from remote.
            val bundle = Bundle()
            bundle.putInt("id",id)
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment)
                .commit()
        }
    }

    fun searchProducts(){
        val query = arguments?.getString("searchQuery")
        lifecycleScope.launch (Dispatchers.IO){
            val db = AppDatabase.getDatabase(requireContext())
            val call = ApiService.getInstance().getSearchProducts(query?:"")
            val searchResultProducts = call.body()!!.products
            if (searchResultProducts.isEmpty()){
                return@launch
            }
            withContext(Dispatchers.Main){
                binding.rvContainer.layoutManager = LinearLayoutManager(requireContext())
                val finalSearchProducts = searchResultProducts.map {
                    it ->
                    Product(
                        averageRating = it.averageRating,
                        categoryId = it.categoryId,
                        productId = it.productId,
                        price = it.price,
                        productImageUrl = it.productImageUrl,
                        productName = it.productName,
                        subcategoryName = "Phones",
                        subCategoryId = it.subCategoryId,
                        description = it.description,
                        categoryName = "Mobiles"
                    )
                }
                binding.rvContainer.adapter = SubCategoryProductsListAdapter(
                    finalSearchProducts,
                    db,
                    viewLifecycleOwner.lifecycleScope,
                    itemClickListener
                )
            }
        }
    }
}
