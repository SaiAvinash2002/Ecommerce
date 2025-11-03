package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.model.local.CartEntity
import com.example.ecommerce.databinding.CheckoutItemBinding

class CheckOutAdapter(val productsList: List<CartEntity>): RecyclerView.Adapter<CheckOutAdapter.CheckOutItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckOutItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CheckoutItemBinding.inflate(inflater,parent,false)
        return CheckOutItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CheckOutItemViewHolder,
        position: Int
    ) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int = productsList.size

    inner class CheckOutItemViewHolder(val binding: CheckoutItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: CartEntity){
            binding.tvProductName.text = item.product_name
            binding.tvProductUnitPrice.text = "Price: ${item.price}"
            binding.tvProductQuantity.text = "Quantity: ${item.quantity.toString()}"
            binding.tvProductAmount.text = "Amount: ${(item.price.toDouble()*item.quantity).toString()}"
        }
    }
}