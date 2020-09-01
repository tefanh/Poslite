package com.tefanhaetami.poslite.screens.product

data class ProductFormState(val categoryIdError: Int? = null,
                            val titleError: Int? = null,
                            val priceError: Int? = null,
                            val stockError: Int? = null,
                            val imageError: Int? = null,
                            val descriptionError: Int? = null,
                            val isDataValid: Boolean = false)