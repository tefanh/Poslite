package com.tefanhaetami.poslite.screens.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCategoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_category)
        setSupportActionBar(binding.categoryToolbar)
        val navController = findNavController(R.id.categoryNavHostFragment)
        val listener = AppBarConfiguration.OnNavigateUpListener {
            if (!navController.navigateUp()) {
                onBackPressed()
            }
            navController.navigateUp()
        }
        val configuration = AppBarConfiguration.Builder().setFallbackOnNavigateUpListener(listener).build()
        NavigationUI.setupActionBarWithNavController(this, navController, configuration)
        NavigationUI.setupWithNavController(binding.categoryToolbar, navController, configuration)
    }
}