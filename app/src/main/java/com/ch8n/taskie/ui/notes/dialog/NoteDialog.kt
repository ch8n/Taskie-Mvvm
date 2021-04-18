package com.ch8n.taskie.ui.notes.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.taskie.data.utils.ViewBindingBottomSheet
import com.ch8n.taskie.databinding.DialogAddNoteBinding

class NoteDialog : ViewBindingBottomSheet<DialogAddNoteBinding>() {

    companion object {
        const val TAG = "NoteDialog"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogAddNoteBinding
        get() = DialogAddNoteBinding::inflate

    override fun setup() {

    }
}