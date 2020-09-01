package com.tefanhaetami.poslite.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tefanhaetami.poslite.database.iface.Category
import com.tefanhaetami.poslite.database.iface.Product
import com.tefanhaetami.poslite.database.dao.CategoryDao
import com.tefanhaetami.poslite.database.dao.ProductDao

@Database(entities = [Category::class, Product::class], version = 1, exportSchema = false)
abstract class PosliteDatabase : RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val productDao: ProductDao

    companion object {
        @Volatile
        private var INSTANCE: PosliteDatabase? = null

        fun getInstance(context: Context): PosliteDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PosliteDatabase::class.java,
                        "poslite_database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}