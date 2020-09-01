package com.tefanhaetami.poslite.screens.product

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.database.PosliteDatabase
import com.tefanhaetami.poslite.databinding.FragmentProductBinding

class ProductFragment : Fragment() {
    private val TAG = "ProductFragment"

    lateinit var productViewModel: ProductViewModel
    private lateinit var binding: FragmentProductBinding

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = PosliteDatabase.getInstance(application).productDao
        val viewModelFactory = ProductViewModelFactory(dataSource, application)
        productViewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)
        binding.productViewModel = productViewModel
        binding.lifecycleOwner = this

//        val manager = LinearLayoutManager(activity)
//        manager.orientation = LinearLayoutManager.VERTICAL
//        binding.categoryList.layoutManager = manager
//
//        val adapter = CategoryAdapter(CategoryListener {
//            productViewModel.startNavigateToDetail(it)
//        })
//        binding.categoryList.adapter = adapter
//        productViewModel.categories.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

        productViewModel.navigateToCreate.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(ProductFragmentDirections.actionProductFragmentToProductCreateFragment())
                productViewModel.doneNavigateToCreate()
            }
        })

        productViewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(ProductFragmentDirections.actionProductFragmentToProductDetailFragment(it))
                productViewModel.doneNavigateToDetail()
            }
        })

        return binding.root
    }

    private fun imagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    imagePicker()
                }
                else{
                    //permission from popup denied
                    Log.d(TAG, "onRequestPermissionsResult: " + "Permission denied!")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}