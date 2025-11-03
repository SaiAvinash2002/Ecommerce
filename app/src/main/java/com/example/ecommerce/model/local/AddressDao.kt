package com.example.ecommerce.model.local

import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AddressDao {
    @Insert
    suspend fun addAddress(address: AddressEntity)
// Insert returns id or Unit
    @Query("SELECT * FROM address_db")
    fun getAddresses(): LiveData<List<AddressEntity>>
}