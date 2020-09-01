package com.tefanhaetami.poslite.screens.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.dao.CategoryDao
import com.tefanhaetami.poslite.database.iface.Category
import kotlinx.coroutines.*

class CategoryEditViewModel (val database: CategoryDao, application: Application, categoryId: Long): AndroidViewModel(application) {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val category: LiveData<Category?> = database.find(categoryId)

    private val _navigateUp = MutableLiveData<Boolean>()
    val navigateUp: LiveData<Boolean> get() = _navigateUp

    private val _categoryForm = MutableLiveData<CategoryFormState>()
    val categoryForm: LiveData<CategoryFormState> get() = _categoryForm

    fun startNavigateUp() {
        _navigateUp.value = true
    }

    fun doneNavigateUp() {
        _navigateUp.value = false
    }

    fun onUpdate(title: String, description: String) {
        category.value?.let {
            uiScope.launch {
                val ref = Category(it.id, title, description)
                update(ref)
                _navigateUp.value = true
            }
        }
    }

    private suspend fun update(category: Category) {
        withContext(Dispatchers.IO) {
            database.update(category)
        }
    }

    fun categoryDataChanged(title: String, description: String) {
        if (!isTitleValid(title)) {
            _categoryForm.value = CategoryFormState(titleError = R.string.not_a_valid_title)
        } else {
            _categoryForm.value = CategoryFormState(isDataValid = true)
        }
    }

    private fun isTitleValid(title: String): Boolean {
        return title.length > 0
    }
}