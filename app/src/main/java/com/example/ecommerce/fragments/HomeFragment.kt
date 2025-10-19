package com.example.ecommerce.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerce.CategoryAdapter
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.model.CategoryResponse
import com.example.ecommerce.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding
    val apiService: ApiService = ApiService.getInstance()

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
        loadCategories()
    }

    fun loadCategories(){
        val call = apiService.getCategories()

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if(!response.isSuccessful) {
                    Log.d("Error", "Failed to process request. Error is: ${response.errorBody()}")
                    return
                }

                val result = response.body()

                if (result != null) {
                    binding.rvContainer.layoutManager = GridLayoutManager(requireActivity(),2)
                    binding.rvContainer.adapter = CategoryAdapter(result.categories) { category ->
                        openSubCategoryFragment(category.categoryId.toInt())
                    }
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.e("API_FAILURE", "Error: ${t.message}")
            }
        })
    }

            fun openSubCategoryFragment(categoryId: Int){
                when(categoryId){
                    1-> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, SmartPhonesFragment())
                            .addToBackStack("HomeFragment")
                            .commit()
                    }
                    2-> {
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, LaptopsFragment())
                            .addToBackStack("HomeFragment")
                            .commit()
                    }
                }
            }
}

