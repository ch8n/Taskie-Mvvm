package com.ch8n.taskie.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ch8n.taskie.R
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentLoginBinding
import com.ch8n.taskie.di.Injector
import com.google.android.material.snackbar.Snackbar


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {

    private val taskiePrefs by lazy { Injector.taskiePrefs }
    private val router by lazy { findNavController() }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setup() {

        val greetingMessage = Snackbar.make(
            binding.root,
            "Hi ${taskiePrefs.userName}! Welcome back",
            Snackbar.LENGTH_LONG
        )
        if (taskiePrefs.isLogin) {
            greetingMessage.show()
        }

        binding.loader.visibility = View.VISIBLE
        android.os.Handler().postDelayed({
            taskiePrefs.isLogin = true
            taskiePrefs.userName = "Chetan"
            greetingMessage.dismiss()

            val gotoHomeFragment = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
            router.navigate(gotoHomeFragment)

        }, 2000)

    }

}