package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.model.local.CartEntity
import com.example.ecommerce.Product
import com.example.ecommerce.R
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.databinding.ItemProductBinding
import com.example.ecommerce.viewmodel.ItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Since we're updating the DB we need the lifecycleScope and the DB object as well

class SubCategoryProductsListAdapter(
    private val productList: List<Product>,
    private val db: AppDatabase,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<SubCategoryProductsListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
//            Set On click listener on the entire element & navigate user to the product details
            binding.root.setOnClickListener {
//                getRemoteProductDetails()
                itemClickListener.onClick(product.productId.toInt())

            }
            binding.tvTitle.text = product.productName
            binding.tvPrice.text = "$${product.price}"
            binding.tvDescription.text = product.description
            binding.rbProduct.rating = 4.0.toFloat()
            val imageResId = when (product.productId) {
                "1" -> R.drawable.img
                "2" -> R.drawable.img_1
                else -> R.drawable.img
            }
            binding.ivProduct.setImageResource(imageResId);


            // default UI state
            binding.tvAddToCart.visibility = View.VISIBLE
            binding.llQuantity.visibility = View.GONE
            binding.tvQuantity.text = "1"

            // Check DB for existing item
            lifecycleScope.launch {
                val existing = withContext(Dispatchers.IO) {
                    db.cartDao().getItemByProductId(product.productId)
                }
                if (existing != null) {
                    // show quantity UI with current qty
                    binding.tvAddToCart.visibility = View.GONE
                    binding.llQuantity.visibility = View.VISIBLE
                    binding.tvQuantity.text = existing.quantity.toString()
                } else {
                    binding.tvAddToCart.visibility = View.VISIBLE
                    binding.llQuantity.visibility = View.GONE
                }
            }

            // Add to cart button
            binding.tvAddToCart.setOnClickListener {
                // create cart item
                val cartItem = CartEntity(
                    product_id = product.productId,
                    product_name = product.productName,
                    description = product.description,
                    category_name = product.categoryName,
                    subcategory_name = product.subcategoryName,
                    price = product.price,
                    product_image_url = product.productImageUrl,
                    quantity = 1
                )

                lifecycleScope.launch (Dispatchers.Main){
                    withContext(Dispatchers.IO) {
                        db.cartDao().insertItem(cartItem)
                    }
                    // update UI on main
                    binding.tvAddToCart.visibility = View.GONE
                    binding.llQuantity.visibility = View.VISIBLE
                    binding.tvQuantity.text = "1"
                }
            }

            // + button
            binding.btnPlus.setOnClickListener {
                lifecycleScope.launch {
                    // get existing
                    val existing = withContext(Dispatchers.IO) {
                        db.cartDao().getItemByProductId(product.productId)
                    }
                    val newQty = (existing?.quantity ?: 0) + 1
                    withContext(Dispatchers.IO) {
                        db.cartDao().updateQuantity(product.productId, newQty)
                    }
                    binding.tvQuantity.text = newQty.toString()
                }
            }

            // - button
            binding.btnMinus.setOnClickListener {
                lifecycleScope.launch (Dispatchers.Main){
                    val existing = withContext(Dispatchers.IO) {
                        db.cartDao().getItemByProductId(product.productId)
                    }
                    val currentQty = existing?.quantity ?: 0
                    val newQty = currentQty - 1
                    if (newQty > 0) {
                        withContext(Dispatchers.IO) {
                            db.cartDao().updateQuantity(product.productId, newQty)
                        }
                        binding.tvQuantity.text = newQty.toString()
                    } else {
                        // delete from DB and revert UI
                        withContext(Dispatchers.IO) {
                            db.cartDao().deleteByProductId(product.productId)
                        }
                        binding.tvAddToCart.visibility = View.VISIBLE
                        binding.llQuantity.visibility = View.GONE
                        binding.tvQuantity.text = "1"
                    }
                }
            }
        }
    }
}