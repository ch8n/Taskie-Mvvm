package com.ch8n.taskie.ui.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentLoginBinding
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.router.Router


class LoginFragment : ViewBindingFragment<FragmentLoginBinding>() {

    private val SIGN_IN = 1001

    private val takiePrefs by lazy { Injector.taskiePrefs }
    private val router by lazy {
        requireActivity() as? Router ?: throw IllegalStateException("Activity needs to be Router")
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLoginBinding
        get() = FragmentLoginBinding::inflate

    override fun setup()  {

//        binding.signInButton.setSize(SignInButton.SIZE_WIDE);
//        binding.signInButton.visibility = View.GONE
        binding.loader.visibility = View.VISIBLE

//        binding.signInButton.setOnClickListener {
//            val account = GoogleSignIn.getLastSignedInAccount(requireContext())
//            if (account != null) {
//                onAccountInfo(account)
//            } else {
//                // request login info
//                val googleSignInOptions =
//                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestEmail()
//                        .build()
//                val signInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions);
//                val signInIntent: Intent = signInClient.signInIntent
//                // TODO migrate to new callback API
//                startActivityForResult(signInIntent, SIGN_IN)
//            }
//        }


        android.os.Handler().postDelayed({
                router.toHomeScreen()
//            if (takiePrefs.isLogin) {
//                router.toHomeScreen()
//            }else{
//                binding.loader.visibility = View.GONE
//                binding.signInButton.visibility = View.VISIBLE
//            }
        },2000)

    }

//    fun onAccountInfo(account: GoogleSignInAccount?) = with(takiePrefs) {
//        if (account == null) {
//            Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_LONG).show()
//            return@with
//        }
//        userName = account.displayName ?: "Stranger"
//        userEmail = account.email ?: "lost.soul@email.gone"
//        socialId = account.id ?: ""
//        avatarUrl = account.photoUrl?.toString() ?: ""
//        isLogin = true
//        android.os.Handler().postDelayed({
//            binding.loader.visibility = View.VISIBLE
//            binding.signInButton.visibility = View.GONE
//            router.toHomeScreen()
//        },2000)
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            val account = runCatching { task.getResult(ApiException::class.java) }.getOrNull()
//            onAccountInfo(account)
//        }
//    }


}