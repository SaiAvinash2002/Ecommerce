package com.example.ecommerce.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentCartBinding
import com.example.ecommerce.databinding.FragmentCartItemsBinding
import com.example.ecommerce.databinding.FragmentSummaryBinding
import com.example.ecommerce.view.CheckOutAdapter
import com.example.ecommerce.view.OrderConfirmation
import com.example.ecommerce.viewmodel.CheckOutViewModel

// This SummaryFragment uses CheckOutViewModel in MVVM

class SummaryFragment : Fragment() {
    private lateinit var binding: FragmentSummaryBinding
    private lateinit var checkOutViewModel: CheckOutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSummaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        createRecyclerView()
        loadItemsSummary()
        handleNavigation()
    }

    private fun createViewModel() {
        checkOutViewModel = ViewModelProvider(requireActivity()).get(CheckOutViewModel::class.java)
    }

    private fun createRecyclerView() {
        binding.rvContainer.layoutManager = LinearLayoutManager(requireActivity())
    }

    fun loadItemsSummary(){
        checkOutViewModel.cartItems.observe(viewLifecycleOwner){
            cartList ->
            binding.rvContainer.adapter = CheckOutAdapter(cartList)
            binding.tvTotalAmountValue.text= "$${(cartList.sumOf{ cartItem ->
            cartItem.price.toDouble() * cartItem.quantity.toInt()
        }).toString()
    }"
        }
    }

    fun handleNavigation(){
        binding.btnNext.setOnClickListener {
            startActivity(Intent(requireActivity(), OrderConfirmation::class.java))
        }
    }
}