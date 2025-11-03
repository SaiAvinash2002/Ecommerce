package com.example.ecommerce.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CartEntity::class, AddressEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun addressDao(): AddressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cart_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}

val migrations_1_2 = object : Migration(1,2){
    override fun migrate(db: SupportSQLiteDatabase) {
        super.migrate(db)
        db.execSQL("""
           CREATE TABLE IF NOT EXISTS address_db (
                address_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                user_id INTEGER NOT NULL,
                title TEXT NOT NULL,
                address TEXT NOT NULL
            )
        """.trimIndent())

    }
}