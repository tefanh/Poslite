package com.tefanhaetami.poslite.database.iface

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "categoryId")
    val categoryId: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Long,
    @ColumnInfo(name = "stock")
    val stock: Long,
    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray,
    @ColumnInfo(name = "description")
    val description: String?
)