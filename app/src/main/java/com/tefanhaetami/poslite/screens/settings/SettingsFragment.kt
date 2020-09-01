package com.tefanhaetami.poslite.screens.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.tefanhaetami.poslite.R
import com.tefanhaetami.poslite.databinding.FragmentSettingsBinding
import com.tefanhaetami.poslite.screens.category.CategoryActivity
import com.tefanhaetami.poslite.screens.product.ProductActivity

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        val settingsViewModel = SettingsViewModel()
        binding.settingsViewModel = settingsViewModel
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, binding.settingsViewModel!!.options);
        binding.settingsList.adapter = adapter
        binding.settingsList.setOnItemClickListener { adapterView, view, i, l ->
            when(i) {
                0 -> let {
                    val intent = Intent(activity, CategoryActivity::class.java)
                    startActivity(intent)
                }

                1 -> let {
                    val intent = Intent(activity, ProductActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        return binding.root
    }
}