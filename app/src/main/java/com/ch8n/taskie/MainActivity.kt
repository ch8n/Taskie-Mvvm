package com.ch8n.taskie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ch8n.taskie.data.utils.ViewBindingActivity
import com.ch8n.taskie.databinding.ActivityMainBinding
import com.ch8n.taskie.ui.home.HomeFragment
import com.ch8n.taskie.ui.login.LoginFragment
import com.ch8n.taskie.ui.router.Router

class MainActivity : ViewBindingActivity<ActivityMainBinding>(), Router {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup(): Unit = with(binding) {
        toLoginScreen()
    }

    override fun toLoginScreen() {
        supportFragmentManager
            .beginTransaction()
            .add(binding.container.id, LoginFragment())
            .commit()
    }

    override fun toHomeScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.container.id, HomeFragment())
            .commit()
    }
}