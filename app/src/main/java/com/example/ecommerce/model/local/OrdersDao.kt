package com.example.ecommerce.model.local

import androidx.room.Dao
import androidx.room.Insert
import com.example.ecommerce.model.remote.dto.OrderRequest

@Dao
interface OrdersDao {
    @Insert
    suspend fun insertOrder(order: OrderRequest)
}