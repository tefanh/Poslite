package com.tefanhaetami.poslite.screens.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tefanhaetami.poslite.database.dao.ProductDao

class ProductDetailViewModelFactory (
    private val datasource: ProductDao,
    private val application: Application,
    private val productId: Long
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(datasource, application, productId) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}