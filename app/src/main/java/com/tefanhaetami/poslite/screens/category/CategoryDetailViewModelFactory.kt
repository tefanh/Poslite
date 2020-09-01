package com.tefanhaetami.poslite.screens.category

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tefanhaetami.poslite.database.dao.CategoryDao

class CategoryDetailViewModelFactory (
    private val datasource: CategoryDao,
    private val application: Application,
    private val categoryId: Long
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryDetailViewModel::class.java)) {
            return CategoryDetailViewModel(datasource, application, categoryId) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}