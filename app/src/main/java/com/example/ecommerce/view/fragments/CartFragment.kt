package com.example.ecommerce.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.model.local.CartEntity
import com.example.ecommerce.view.CartAdapter
import com.example.ecommerce.view.CheckOutActivity
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.databinding.FragmentCartBinding
import com.example.ecommerce.view.CheckOutAdapter
import com.example.ecommerce.viewmodel.CartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// This CartFragment uses CartViewModel in MVVM
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewModel()
        setUpRecyclerView()
        loadCart()
        handleCheckOut()
    }

    fun setUpViewModel(){
        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
    }

    fun setUpRecyclerView(){
        binding.rvContainer.layoutManager = LinearLayoutManager(requireActivity())
    }

    fun loadCart() {
          // Get LiveData<List<CartEntity>>
        cartViewModel.cartItems.observe(viewLifecycleOwner) {
                list ->
            binding.rvContainer.adapter = CartAdapter(list as MutableList<CartEntity>, cartViewModel.dao, viewLifecycleOwner.lifecycleScope)
            val total = list.sumOf { it.price.toDouble() * it.quantity }
            binding.tvCartPrice.text = "$${"%.2f".format(total)}"
        }
    }

    fun handleCheckOut(){
        binding.btnCheckOut.setOnClickListener {
            val intent = Intent(activity, CheckOutActivity::class.java)
            startActivity(intent)
        }
    }
}