package com.tefanhaetami.poslite.screens.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.PosliteDatabase
import com.tefanhaetami.poslite.databinding.FragmentCategoryDetailBinding

class CategoryDetailFragment: Fragment() {
    private val TAG = "CategoryDetailFragment"

    lateinit var categoryDetailViewModel: CategoryDetailViewModel
    private lateinit var binding: FragmentCategoryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category_detail, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PosliteDatabase.getInstance(application).categoryDao
        val arguments = CategoryDetailFragmentArgs.fromBundle(requireArguments())
        val viewModelFactory = CategoryDetailViewModelFactory(dataSource, application, arguments.id)
        categoryDetailViewModel = ViewModelProvider(this, viewModelFactory).get(CategoryDetailViewModel::class.java)
        binding.categoryDetailViewModel = categoryDetailViewModel
        binding.lifecycleOwner = this
        categoryDetailViewModel.category.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.category = it
            }
        })

        initListener()
        initNavigation()

        return binding.root
    }

    private fun initListener() {
        binding.deleteButton.setOnClickListener {
            doDelete()
        }
    }

    private fun initNavigation() {
        categoryDetailViewModel.navigateUp.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigateUp()
                categoryDetailViewModel.doneNavigateUp()
            }
        })

        categoryDetailViewModel.navigateToEdit.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(CategoryDetailFragmentDirections.actionCategoryDetailFragmentToEditCategoryFragment(it))
                categoryDetailViewModel.doneNavigateToEdit()
            }
        })
    }

    private fun doDelete() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_category))
            .setMessage(resources.getString(R.string.are_you_sure_want_to_delete_this_category))
            .setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                categoryDetailViewModel.onDelete(requireNotNull(binding.category).id)
            }
            .show()
    }
}