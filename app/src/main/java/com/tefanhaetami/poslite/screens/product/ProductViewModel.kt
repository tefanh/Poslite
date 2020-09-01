package com.tefanhaetami.poslite.screens.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tefanhaetami.poslite.database.dao.ProductDao

class ProductViewModel (val database: ProductDao, application: Application): AndroidViewModel(application) {
    val products = database.getAll()

    private val _navigateToDetail = MutableLiveData<Long?>()
    val navigateToDetail: LiveData<Long?> get() = _navigateToDetail

    private val _navigateToCreate = MutableLiveData<Boolean>()
    val navigateToCreate: LiveData<Boolean> get() = _navigateToCreate

    fun startNavigateToDetail(id: Long) {
        _navigateToDetail.value = id
    }

    fun doneNavigateToDetail() {
        _navigateToDetail.value = null
    }

    fun startNavigateToCreate() {
        _navigateToCreate.value = true
    }

    fun doneNavigateToCreate() {
        _navigateToCreate.value = false
    }
}
