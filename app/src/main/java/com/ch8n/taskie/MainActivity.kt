package com.ch8n.taskie

import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ch8n.taskie.data.utils.ViewBindingActivity
import com.ch8n.taskie.databinding.ActivityMainBinding
import com.ch8n.taskie.ui.home.HomeFragment


class MainActivity : ViewBindingActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup(): Unit = with(binding) {
        val navFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        navController = navFragment.navController
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.label) {
            getString(R.string.nav_home_lable) -> {
                val navFragment = supportFragmentManager
                    .findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
                val homeFragment = navFragment.childFragmentManager.fragments[0] as? HomeFragment
                if (homeFragment?.isOnFirstPagerPage() == true) {
                    finish()
                }
            }
            else -> super.onBackPressed()
        }
    }
}