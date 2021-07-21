package com.ch8n.taskie.ui.login

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.taskie.data.utils.ViewBindingBottomSheet
import com.ch8n.taskie.databinding.DialogSetupProfileBinding
import com.ch8n.taskie.di.Injector


class SetupProfileDialog : ViewBindingBottomSheet<DialogSetupProfileBinding>() {

    companion object {
        const val TAG = "SetupProfileDialog"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogSetupProfileBinding
        get() = DialogSetupProfileBinding::inflate

    private val taskiePrefs = Injector.taskiePrefs

    override fun setup(): Unit = with(binding) {
        btnCreateProfile.setOnClickListener {
            val name = editName.text.toString()
            if (name.isNotEmpty()) {
                taskiePrefs.userName = name
                taskiePrefs.isLogin = true
                val dialogInteraction = (parentFragment as? DialogInteraction)
                    ?: throw IllegalStateException("Implement DialogInteraction")
                dialogInteraction.dismissDialog()
            }
        }
    }

}

interface DialogInteraction {
    fun dismissDialog()
}
