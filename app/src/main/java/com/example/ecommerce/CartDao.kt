package com.example.ecommerce.data

import androidx.room.*
import com.example.ecommerce.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(cartItem: CartEntity)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllItems(): List<CartEntity>

    @Query("SELECT * FROM cart_items WHERE product_id = :productId LIMIT 1")
    suspend fun getItemByProductId(productId: String): CartEntity?

    @Query("DELETE FROM cart_items WHERE product_id = :productId")  // Handling - in cart items
    suspend fun deleteByProductId(productId: String)
    @Delete
    suspend fun deleteItem(cartItem: CartEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("UPDATE cart_items SET quantity = :quantity WHERE product_id = :productId")
    suspend fun updateQuantity(productId: String, quantity: Int)
}
