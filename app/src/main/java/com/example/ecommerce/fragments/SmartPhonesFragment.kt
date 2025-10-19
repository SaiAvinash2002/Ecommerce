package com.example.ecommerce.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.*
import com.example.ecommerce.data.AppDatabase
import com.example.ecommerce.databinding.FragmentSmartPhonesBinding
import com.example.ecommerce.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmartPhonesFragment : Fragment() {

    private lateinit var binding: FragmentSmartPhonesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmartPhonesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadSubCategoryProductsList()
    }

    private fun loadSubCategoryProductsList() {
        val call = ApiService.getInstance().getSubCategoryProductsList(1)

        call.enqueue(object : Callback<SubCategoryProductsListResponse> {
            override fun onResponse(
                call: Call<SubCategoryProductsListResponse?>,
                response: Response<SubCategoryProductsListResponse?>
            ) {
                if (!response.isSuccessful) {
                    Log.d("Error", "Failed to process request. Error: ${response.errorBody()}")
                    return
                }

                val result = response.body()
                if (result != null) {
                    val db = AppDatabase.getDatabase(requireContext())
                    binding.rvContainer.layoutManager = LinearLayoutManager(requireActivity())
                    binding.rvContainer.adapter =
                        SubCategoryProductsListAdapter(result.products, db, viewLifecycleOwner.lifecycleScope)
                }
            }

            override fun onFailure(call: Call<SubCategoryProductsListResponse?>, t: Throwable) {
                Toast.makeText(requireActivity(), "Failed to load products", Toast.LENGTH_LONG).show()
            }
        })
    }
}
