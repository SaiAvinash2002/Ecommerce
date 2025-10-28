package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.model.local.CartEntity
import com.example.ecommerce.R
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.databinding.ItemProductBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartAdapter(
    private val cartList: MutableList<CartEntity>,
    private val db: AppDatabase,
    private val lifecycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position], position)
    }

    override fun getItemCount(): Int = cartList.size

    inner class CartViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: CartEntity, position: Int) {
            binding.tvTitle.text = product.product_name
            binding.tvPrice.text = "$${product.price}"
            binding.tvDescription.text = product.description

            Picasso.get()
                .load(R.drawable.img)
                .resize(100, 140)       // match ImageView size
                .centerCrop()           // scale and crop
                .into(binding.ivProduct)


            // quantity
            binding.tvAddToCart.visibility = View.GONE
            binding.llQuantity.visibility = View.VISIBLE
            binding.tvQuantity.text = product.quantity.toString()

            binding.btnPlus.setOnClickListener {
                val newQty = product.quantity + 1
                lifecycleScope.launch(Dispatchers.IO) {
                    db.cartDao().updateQuantity(product.product_id, newQty)
                    product.quantity = newQty
                    withContext(Dispatchers.Main) {
                        binding.tvQuantity.text = newQty.toString()
                        notifyItemChanged(position)
                    }
                }
            }

            binding.btnMinus.setOnClickListener {
                val newQty = product.quantity - 1
                if (newQty > 0) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        db.cartDao().updateQuantity(product.product_id, newQty)
                        product.quantity = newQty
                        withContext(Dispatchers.Main) {
                            binding.tvQuantity.text = newQty.toString()
                            notifyItemChanged(position)
                        }
                    }
                } else {
                    lifecycleScope.launch(Dispatchers.IO) {
                        db.cartDao().deleteByProductId(product.product_id)
                        withContext(Dispatchers.Main) {
                            cartList.removeAt(position)
                            notifyItemRemoved(position)
                        }
                    }
                }
            }
        }
    }
}
