package com.ch8n.taskie.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentLoginBinding
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.router.Router
import com.ch8n.taskie.utils.setVisibility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>(), DialogInteraction {

    private val taskiePrefs by lazy { Injector.taskiePrefs }

    private val router by lazy {
        requireActivity() as? Router ?: throw IllegalStateException("Activity needs to be Router")
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setup() {

        binding.loader.setVisibility(true)

        lifecycleScope.launch {
            delay(2000)
            if (taskiePrefs.isLogin) {
                router.toHomeScreen()
            } else {
                binding.loader.setVisibility(false)
                binding.setupProfile.setVisibility(true)
            }
        }

        val profileDialog = SetupProfileDialog()

        binding.setupProfile.setOnClickListener {
            val fragment = childFragmentManager.findFragmentByTag(SetupProfileDialog.TAG)
            if (fragment is SetupProfileDialog && fragment.isVisible) {
                fragment.dismiss()
            }
            profileDialog.show(childFragmentManager, SetupProfileDialog.TAG)
        }
    }

    override fun dismissDialog() {
        lifecycleScope.launch(Dispatchers.Main) {

            val fragment = childFragmentManager.findFragmentByTag(SetupProfileDialog.TAG)
            if (fragment is SetupProfileDialog && fragment.isVisible) {
                fragment.dismiss()
            }
            binding.setupProfile.setVisibility(false)
            binding.loader.setVisibility(true)
            delay(1000)
            if (taskiePrefs.isLogin) {
                router.toHomeScreen()
            } else {
                binding.loader.setVisibility(false)
                binding.setupProfile.setVisibility(true)
            }
        }
    }

}