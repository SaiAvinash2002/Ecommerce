package com.example.ecommerce.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommerce.model.local.CartEntity

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertItem(cartItem: CartEntity)

    @Query("SELECT * FROM cart_items")
    fun getAllItems(): LiveData<List<CartEntity>>

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