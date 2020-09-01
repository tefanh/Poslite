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
import com.tefanhaetami.poslite.databinding.FragmentCategoryEditBinding
import com.tefanhaetami.poslite.hideKeyboard

class CategoryEditFragment : Fragment() {
    lateinit var categoryEditViewModel: CategoryEditViewModel
    private lateinit var binding: FragmentCategoryEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_edit, container, false)
        val application = requireNotNull(this.activity).application
        val datasource = PosliteDatabase.getInstance(application).categoryDao
        val arguments = CategoryEditFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = CategoryEditViewModelFactory(datasource, application, arguments.id)
        categoryEditViewModel = ViewModelProvider(this, viewModelFactory).get(CategoryEditViewModel::class.java)
        binding.categoryEditViewModel = categoryEditViewModel
        binding.lifecycleOwner = this
        categoryEditViewModel.category.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.category = it
                binding.titleEditText.setText(it.title)
                binding.descriptionEditText.setText(it.description)
            }
        })

        binding.updateButton.setOnClickListener {
            onUpdate()
        }

        categoryEditViewModel.navigateUp.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigateUp()
                categoryEditViewModel.doneNavigateUp()
            }
        })

        categoryEditViewModel.categoryForm.observe(viewLifecycleOwner, Observer {
            binding.updateButton.isEnabled = it.isDataValid

            if (it.titleError != null) {
                binding.titleEditText.error = getString(it.titleError)
            }

            if (it.descriptionError != null) {
                binding.descriptionEditText.error = getString(it.descriptionError)
            }
        })

        binding.titleEditText.doAfterTextChanged {
            it?.let {
                categoryEditViewModel.categoryDataChanged(it.toString(), binding.descriptionEditText.editableText.toString())
            }
        }

        binding.descriptionEditText.doAfterTextChanged {
            it?.let {
                categoryEditViewModel.categoryDataChanged(binding.titleEditText.editableText.toString(), it.toString())
            }
        }

        return binding.root
    }

    private fun onUpdate() {
        hideKeyboard()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.update_category))
            .setMessage(resources.getString(R.string.are_you_sure_want_to_update_this_category))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                categoryEditViewModel.onUpdate(binding.titleEditText.editableText.toString(), binding.descriptionEditText.editableText.toString())
            }
            .show()
    }
}