package com.example.ecommerce

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.CheckoutItemBinding
import com.example.ecommerce.databinding.FragmentPaymentBinding

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
            binding.tvProductAmount.text = item.price
            binding.tvProductQuantity.text = item.quantity.toString()
        }
    }
}