package com.ch8n.taskie.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentNotesBinding

class NotesFragment : ViewBindingFragment<FragmentNotesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesBinding
        get() = FragmentNotesBinding::inflate

    override fun setup() = with(binding){
        text.text = "Notes"
    }
}