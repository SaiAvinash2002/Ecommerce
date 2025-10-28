package com.example.ecommerce.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_db")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val address_id: Int ,
    val user_id: Int,
    val title : String,
    val address : String
)
