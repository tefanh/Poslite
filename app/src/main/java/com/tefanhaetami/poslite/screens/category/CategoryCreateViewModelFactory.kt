package com.tefanhaetami.poslite.screens.category

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tefanhaetami.poslite.database.dao.CategoryDao

class CategoryCreateViewModelFactory (
    private val datasource: CategoryDao,
    private val application: Application
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryCreateViewModel::class.java)) {
            return CategoryCreateViewModel(datasource, application) as T
        }

        throw IllegalArgumentException("Unknown view model class")
    }
}