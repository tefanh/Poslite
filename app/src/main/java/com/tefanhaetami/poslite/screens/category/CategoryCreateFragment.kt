package com.tefanhaetami.poslite.screens.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.PosliteDatabase
import com.tefanhaetami.poslite.databinding.FragmentCategoryCreateBinding
import com.tefanhaetami.poslite.hideKeyboard

class CategoryCreateFragment : Fragment() {
    lateinit var categoryCreateViewModel: CategoryCreateViewModel
    private lateinit var binding: FragmentCategoryCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_create, container, false)
        val application = requireNotNull(this.activity).application
        val datasource = PosliteDatabase.getInstance(application).categoryDao
        val viewModelFactory = CategoryCreateViewModelFactory(datasource, application)
        categoryCreateViewModel = ViewModelProvider(this, viewModelFactory).get(CategoryCreateViewModel::class.java)
        binding.categoryCreateViewModel = categoryCreateViewModel
        binding.lifecycleOwner = this

        binding.createButton.setOnClickListener {
            onInsert()
        }

        categoryCreateViewModel.navigateUp.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigateUp()
                categoryCreateViewModel.doneNavigateUp()
            }
        })

        categoryCreateViewModel.categoryForm.observe(viewLifecycleOwner, Observer {
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
                categoryCreateViewModel.categoryDataChanged(it.toString(), binding.descriptionEditText.editableText.toString())
            }
        }

        binding.descriptionEditText.doAfterTextChanged {
            it?.let {
                categoryCreateViewModel.categoryDataChanged(binding.titleEditText.editableText.toString(), it.toString())
            }
        }

        return binding.root
    }

    private fun onInsert() {
        hideKeyboard()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.create_category))
            .setMessage(resources.getString(R.string.are_you_sure_want_to_create_new_category))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                categoryCreateViewModel.onInsert(binding.titleEditText.editableText.toString(), binding.descriptionEditText.editableText.toString())
            }
            .show()
    }


}