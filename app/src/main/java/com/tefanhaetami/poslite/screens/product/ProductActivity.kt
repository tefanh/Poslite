package com.tefanhaetami.poslite.screens.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        setSupportActionBar(binding.productToolbar)
        val navController = findNavController(R.id.productNavHostFragment)
        val listener = AppBarConfiguration.OnNavigateUpListener {
            if (!navController.navigateUp()) {
                onBackPressed()
            }
            navController.navigateUp()
        }
        val configuration = AppBarConfiguration.Builder().setFallbackOnNavigateUpListener(listener).build()
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        NavigationUI.setupWithNavController(binding.productToolbar, navController, configuration)
    }
}