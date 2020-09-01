package com.tefanhaetami.poslite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tefanhaetami.poslite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainToolbar)
        val navView: BottomNavigationView = binding.bottomNavView
        val navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.dashboardFragment, R.id.settingsFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}