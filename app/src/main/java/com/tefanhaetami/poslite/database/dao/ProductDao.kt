package com.tefanhaetami.poslite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tefanhaetami.poslite.database.iface.Product

@Dao
interface ProductDao {
    @Insert
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM product WHERE id=:id")
    fun find(id: Long): LiveData<Product?>

    @Query("SELECT * FROM product")
    fun getAll(): LiveData<List<Product>>

    @Query("DELETE FROM product WHERE id=:id")
    fun delete(id: Long)

    @Query("DELETE FROM product")
    fun deleteAll()
}