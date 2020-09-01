package com.tefanhaetami.poslite.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tefanhaetami.poslite.database.iface.Category

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)

    @Update
    fun update(category: Category)

    @Query("SELECT * FROM category WHERE id=:id")
    fun find(id: Long): LiveData<Category?>

    @Query("SELECT * FROM category")
    fun getAll(): LiveData<List<Category>>

    @Query("DELETE FROM category WHERE id=:id")
    fun delete(id: Long)

    @Query("DELETE FROM category")
    fun deleteAll()
}