package com.tefanhaetami.poslite.screens.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.dao.ProductDao
import com.tefanhaetami.poslite.database.iface.Product
import kotlinx.coroutines.*

class ProductCreateViewModel(val database: ProductDao, application: Application): AndroidViewModel(application) {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _navigateUp = MutableLiveData<Boolean>()
    val navigateUp: LiveData<Boolean> get() = _navigateUp

    private val _productForm = MutableLiveData<ProductFormState>()
    val productForm: LiveData<ProductFormState> get() = _productForm

    fun startNavigateUp() {
        _navigateUp.value = true
    }

    fun doneNavigateUp() {
        _navigateUp.value = false
    }

    fun onInsert(title: String, description: String) {
        uiScope.launch {
            val ref = Product(0L, 1, title, 1000, 10, ByteArray(10), description)
            insert(ref)
            _navigateUp.value = true
        }
    }

    fun productDataChanged(title: String, description: String) {
        if (!isTitleValid(title)) {
            _productForm.value = ProductFormState(titleError = R.string.not_a_valid_title)
        } else {
            _productForm.value = ProductFormState(isDataValid = true)
        }
    }

    private suspend fun insert(product: Product) {
        withContext(Dispatchers.IO) {
            database.insert(product)
        }
    }

    private fun isTitleValid(title: String): Boolean {
        return title.length > 0
    }
}
