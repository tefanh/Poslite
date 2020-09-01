package com.tefanhaetami.poslite.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.PosliteDatabase
import com.tefanhaetami.poslite.databinding.FragmentProductCreateBinding
import com.tefanhaetami.poslite.hideKeyboard

class ProductCreateFragment : Fragment() {
    lateinit var productCreateViewModel: ProductCreateViewModel
    private lateinit var binding: FragmentProductCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_create, container, false)
        val application = requireNotNull(this.activity).application
        val datasource = PosliteDatabase.getInstance(application).productDao
        val viewModelFactory = ProductCreateViewModelFactory(datasource, application)
        productCreateViewModel = ViewModelProvider(this, viewModelFactory).get(
            ProductCreateViewModel::class.java)
        binding.productCreateViewModel = productCreateViewModel
        binding.lifecycleOwner = this

        binding.createButton.setOnClickListener {
            onInsert()
        }

        productCreateViewModel.navigateUp.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigateUp()
                productCreateViewModel.doneNavigateUp()
            }
        })

        productCreateViewModel.productForm.observe(viewLifecycleOwner, Observer {
            binding.createButton.isEnabled = it.isDataValid

            if (it.titleError != null) {
                binding.titleEditText.error = getString(it.titleError)
            }

            if (it.descriptionError != null) {
                binding.descriptionEditText.error = getString(it.descriptionError)
            }
        })

        binding.titleEditText.doAfterTextChanged {
            it?.let {
                productCreateViewModel.productDataChanged(it.toString(), binding.descriptionEditText.editableText.toString())
            }
        }

        binding.descriptionEditText.doAfterTextChanged {
            it?.let {
                productCreateViewModel.productDataChanged(binding.titleEditText.editableText.toString(), it.toString())
            }
        }

        return binding.root
    }

    private fun onInsert() {
        hideKeyboard()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.create_product))
            .setMessage(resources.getString(R.string.are_you_sure_want_to_create_new_product))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                productCreateViewModel.onInsert(binding.titleEditText.editableText.toString(), binding.descriptionEditText.editableText.toString())
            }
            .show()
    }


}