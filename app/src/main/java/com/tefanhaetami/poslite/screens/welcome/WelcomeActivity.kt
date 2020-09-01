package com.tefanhaetami.poslite.screens.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.tefanhaetami.poslite.MainActivity
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val binding: ActivityWelcomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        binding.enterButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}