package com.ch8n.taskie.ui.notes.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.ViewBindingBottomSheet
import com.ch8n.taskie.databinding.DialogAddNoteBinding

data class NoteDialogBuilder(
    val noteType: NoteType,
    val actionEditOrDelete: Boolean,
    val onNoteAddListener: (Note) -> Unit = {},
    val onNoteCancelListener: () -> Unit = {},
    val onNoteEditListener: (Note) -> Unit = {},
    val onNoteDeleteListener: (Note) -> Unit = {}
)

class NoteDialog : ViewBindingBottomSheet<DialogAddNoteBinding>() {

    companion object {
        const val TAG = "NoteDialog"
    }

    private val builderLiveData = MutableLiveData<NoteDialogBuilder>()
    fun setDialogBuilder(builder: NoteDialogBuilder) {
        builderLiveData.value = builder
    }

    val modifyNote = MutableLiveData<Note>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogAddNoteBinding
        get() = DialogAddNoteBinding::inflate

    override fun setup(): Unit = with(binding) {

        modifyNote.observe(viewLifecycleOwner, Observer {
            it?:return@Observer
            editTitle.setText(it.title)
            editDesc.setText(it.description)
        })

        builderLiveData.observe(viewLifecycleOwner, Observer { builder ->
            builder ?: return@Observer

            if (builder.actionEditOrDelete) {
                containerUpdate.visibility = View.VISIBLE
                containerCreate.visibility = View.GONE
            } else {
                containerUpdate.visibility = View.GONE
                containerCreate.visibility = View.VISIBLE
            }

            btnAdd.setOnClickListener {
                val note = when (builder.noteType) {
                    is NoteType.Note -> Note.defaultNote(
                        title = editTitle.text.toString(),
                        description = editDesc.text.toString()
                    )
                    is NoteType.Todo -> Note.defaultTask(
                        title = editTitle.text.toString(),
                        description = editDesc.text.toString()
                    )
                }
                builder.onNoteAddListener.invoke(note)
                dismiss()
            }

            btnCancel.setOnClickListener {
                builder.onNoteCancelListener.invoke()
                dismiss()
            }

            btnDelete.setOnClickListener {
                val deleteNote = modifyNote.value ?: return@setOnClickListener
                builder.onNoteDeleteListener.invoke(deleteNote)
                dismiss()
            }

            btnUpdate.setOnClickListener {
                val note = modifyNote.value ?: return@setOnClickListener
                val updatedNote = note.copy(
                    title = editTitle.text.toString(),
                    description = editDesc.text.toString()
                )
                builder.onNoteEditListener.invoke(updatedNote)
                dismiss()
            }
        })
    }
}
