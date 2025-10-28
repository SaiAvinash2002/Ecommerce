package com.example.ecommerce.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentCartItemsBinding
import com.example.ecommerce.view.CheckOutAdapter
import com.example.ecommerce.viewmodel.CheckOutViewModel
import androidx.fragment.app.activityViewModels

// Here we're using SharedViewModel.
// So, CartItemsFragment uses CheckoutViewModel
class CartItemsFragment : Fragment() {
    private lateinit var binding: FragmentCartItemsBinding
    private val checkOutViewModel: CheckOutViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartItemsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleNavigation()
//        viewLifecycleOwner refers to the lifecycle of the fragment’s view
        checkOutViewModel.cartItems.observe(viewLifecycleOwner){
            cartList ->
            val total = cartList.sumOf { it.price.toDouble() * it.quantity }
            binding.tvTotalAmountValue.text = "$$total"
        }
    }

    fun handleNavigation() {
        binding.rvContainer.layoutManager = LinearLayoutManager(requireActivity())

        checkOutViewModel.cartItems.observe(viewLifecycleOwner) { cartList ->
            binding.rvContainer.adapter = CheckOutAdapter(cartList)
        }

        binding.btnNext.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.vpContainer)
            viewPager.currentItem = viewPager.currentItem + 1
        }
    }
}