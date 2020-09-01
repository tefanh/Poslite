package com.tefanhaetami.poslite.screens.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.PosliteDatabase
import com.tefanhaetami.poslite.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private val TAG = "CategoryFragment"

    lateinit var categoryViewModel: CategoryViewModel
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PosliteDatabase.getInstance(application).categoryDao
        val viewModelFactory = CategoryViewModelFactory(dataSource, application)
        categoryViewModel = ViewModelProvider(this, viewModelFactory).get(CategoryViewModel::class.java)
        binding.categoryViewModel = categoryViewModel
        binding.lifecycleOwner = this

        val manager = LinearLayoutManager(activity)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding.categoryList.layoutManager = manager

        val adapter = CategoryAdapter(CategoryListener {
            categoryViewModel.startNavigateToDetail(it)
        })
        binding.categoryList.adapter = adapter
        categoryViewModel.categories.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        categoryViewModel.navigateToCreate.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToCreateCategoryFragment())
                categoryViewModel.doneNavigateToCreate()
            }
        })

        categoryViewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToCategoryDetailFragment(it))
                categoryViewModel.doneNavigateToDetail()
            }
        })

        return binding.root
    }
}