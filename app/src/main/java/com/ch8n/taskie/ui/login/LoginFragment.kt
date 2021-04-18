package com.ch8n.taskie.ui.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentLoginBinding
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.router.Router
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {

    private val SIGN_IN = 1001

    private val takiePrefs by lazy { Injector.taskiePrefs }
    private val router by lazy {
        requireActivity() as? Router ?: throw IllegalStateException("Activity needs to be Router")
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setup() = runBlocking {




//        if (takiePrefs.isLogin) {
//            router.toHomeScreen()
//            return@runBlocking
//        }

        binding.signInButton.setSize(SignInButton.SIZE_WIDE);
        binding.signInButton.setOnClickListener {
            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
            if (account != null) {
                onAccountInfo(account)
            } else {
                // request login info
                val googleSignInOptions =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build()
                val signInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);
                val signInIntent: Intent = signInClient.signInIntent
                startActivityForResult(signInIntent, SIGN_IN)
            }
        }
    }

    fun onAccountInfo(account: GoogleSignInAccount?) = with(takiePrefs) {
        if (account == null) {
            Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_LONG).show()
            return@with
        }
        userName = account.displayName ?: "Stranger"
        userEmail = account.email ?: "lost.soul@email.gone"
        socialId = account.id ?: ""
        avatarUrl = account.photoUrl?.toString() ?: ""
        isLogin = true
        router.toHomeScreen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = runCatching { task.getResult(ApiException::class.java) }.getOrNull()
            onAccountInfo(account)
        }
    }


}