package com.tefanhaetami.poslite.screens.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.dao.ProductDao
import com.tefanhaetami.poslite.database.iface.Product
import kotlinx.coroutines.*

class ProductEditViewModel (val database: ProductDao, application: Application, productId: Long): AndroidViewModel(application) {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    val product: LiveData<Product?> = database.find(productId)

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

    fun onUpdate(title: String, description: String) {
        product.value?.let {
            uiScope.launch {
                val ref = Product(it.id, 1, title, 1, 1, ByteArray(10), description)
                update(ref)
                _navigateUp.value = true
            }
        }
    }

    private suspend fun update(product: Product) {
        withContext(Dispatchers.IO) {
            database.update(product)
        }
    }

    fun categoryDataChanged(title: String, description: String) {
        if (!isTitleValid(title)) {
            _productForm.value = ProductFormState(titleError = R.string.not_a_valid_title)
        } else {
            _productForm.value = ProductFormState(isDataValid = true)
        }
    }

    private fun isTitleValid(title: String): Boolean {
        return title.length > 0
    }
}