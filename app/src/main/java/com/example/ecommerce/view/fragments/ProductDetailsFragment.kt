package com.example.ecommerce.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ProductDetailsBinding
import com.example.ecommerce.model.local.AppDatabase
import com.example.ecommerce.model.local.CartEntity
import com.example.ecommerce.model.remote.ApiClient
import com.example.ecommerce.model.remote.ApiService
import com.example.ecommerce.view.ReviewsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsFragment : Fragment() {

    private lateinit var binding: ProductDetailsBinding
    private lateinit var db: AppDatabase
    private var productId: Int = -1
    private var currentQty = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductDetailsBinding.inflate(inflater, container, false)
        db = AppDatabase.Companion.getDatabase(requireContext())  // Initialize Room DB
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productId = arguments?.getInt("id") ?: return
        getRemoteProductDetails(productId)
    }

    private fun getRemoteProductDetails(id: Int) {
        val api = ApiClient.retrofit.create(ApiService::class.java)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = api.getProductDetails(id)
                val productInfo = response.body()?.product

                withContext(Dispatchers.Main) {
                    if (productInfo == null) {
                        Toast.makeText(requireContext(), "Product not found", Toast.LENGTH_SHORT)
                            .show()
                        return@withContext
                    }

                    // Set UI Data
                    binding.tvTitle.text = productInfo.productName
                    binding.tvProductDescription.text = productInfo.description
                    binding.tvProductPrice.text = "$${productInfo.price}"
                    binding.rbProduct.rating = 4f

                    if (id == 1) binding.imageView.setImageResource(R.drawable.img)
                    else binding.imageView.setImageResource(R.drawable.img_1)
                    binding.tvSpecificationsModelName.text = productInfo.productName
                    // Check if the comments are empty, if empty show the TextView
                    if (productInfo.reviews.isEmpty()) {
                        binding.tvNoComments.visibility = View.VISIBLE
                    }

                    // Check if product already in cart
                    lifecycleScope.launch(Dispatchers.IO) {
                        val cartItem = db.cartDao().getItemByProductId(productInfo.productId)
                        withContext(Dispatchers.Main) {
                            if (cartItem != null) {
                                currentQty = cartItem.quantity
                                showQuantityControls()
                            } else {
                                showAddToCart()
                            }
                        }
                    }

                    // ADD TO CART button
                    binding.tvAddToCart.setOnClickListener {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val cartEntity = CartEntity(
                                product_id = productInfo.productId,
                                product_name = productInfo.productName,
                                price = productInfo.price.toDouble().toString(),
                                quantity = 1,
                                description = productInfo.description,
                                product_image_url = productInfo.productImageUrl,
                                category_name = productInfo.categoryId,
                                subcategory_name = productInfo.subCategoryId,
                            )
                            db.cartDao().insertItem(cartEntity)
                            withContext(Dispatchers.Main) {
                                currentQty = 1
                                showQuantityControls()
                            }
                        }
                    }

                    // PLUS button
                    binding.btnPlus.setOnClickListener {
                        val newQty = currentQty + 1
                        lifecycleScope.launch(Dispatchers.IO) {
                            db.cartDao().updateQuantity(productInfo.productId, newQty)
                            currentQty = newQty
                            withContext(Dispatchers.Main) {
                                binding.tvQuantity.text = newQty.toString()
                            }
                        }
                    }

                    // MINUS button
                    binding.btnMinus.setOnClickListener {
                        val newQty = currentQty - 1
                        if (newQty > 0) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                db.cartDao().updateQuantity(productInfo.productId, newQty)
                                currentQty = newQty
                                withContext(Dispatchers.Main) {
                                    binding.tvQuantity.text = newQty.toString()
                                }
                            }
                        } else {
                            lifecycleScope.launch(Dispatchers.IO) {
                                db.cartDao().deleteByProductId(productInfo.productId)
                                withContext(Dispatchers.Main) {
                                    showAddToCart()
                                }
                            }
                        }
                    }

                    // Setup Reviews RecyclerView
                    productInfo.reviews?.let {
                        binding.recyclerViewReviews.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding.recyclerViewReviews.adapter = ReviewsAdapter(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showQuantityControls() {
        binding.tvAddToCart.visibility = View.GONE
        binding.llQuantity.visibility = View.VISIBLE
        binding.tvQuantity.text = currentQty.toString()
    }

    private fun showAddToCart() {
        binding.llQuantity.visibility = View.GONE
        binding.tvAddToCart.visibility = View.VISIBLE
    }
}