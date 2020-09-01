package com.tefanhaetami.poslite.screens.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tefanhaetami.poslite.database.dao.CategoryDao
import com.tefanhaetami.poslite.database.iface.Category
import kotlinx.coroutines.*

class CategoryDetailViewModel (val database: CategoryDao, application: Application, categoryId: Long): AndroidViewModel(application) {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    val category: LiveData<Category?> = database.find(categoryId)

    private val _navigateUp = MutableLiveData<Boolean>()
    val navigateUp: LiveData<Boolean> get() = _navigateUp

    private val _navigateToEdit = MutableLiveData<Long>()
    val navigateToEdit: LiveData<Long> get() = _navigateToEdit

    fun startNavigateUp() {
        _navigateUp.value = true
    }

    fun doneNavigateUp() {
        _navigateUp.value = false
    }

    fun startNavigateToEdit(id: Long) {
        _navigateToEdit.value = id
    }

    fun doneNavigateToEdit() {
        _navigateToEdit.value = null
    }

    fun onDelete(id: Long) {
        uiScope.launch {
            delete(id)
            _navigateUp.value = true
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.delete(id)
        }
    }
}