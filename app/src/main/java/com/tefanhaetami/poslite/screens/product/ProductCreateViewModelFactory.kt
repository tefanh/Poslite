package com.tefanhaetami.poslite.screens.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tefanhaetami.poslite.database.dao.ProductDao

class ProductCreateViewModelFactory (
    private val datasource: ProductDao,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductCreateViewModel::class.java)) {
            return ProductCreateViewModel(datasource, application) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}