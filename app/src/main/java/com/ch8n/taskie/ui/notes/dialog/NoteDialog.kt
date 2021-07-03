package com.ch8n.taskie.ui.notes.dialog

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.ViewBindingBottomSheet
import com.ch8n.taskie.databinding.DialogAddNoteBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteDialogBuilder(
    val noteType: NoteType,
    val note: Note,
    val actionEditOrDelete: Boolean,
    val onNoteAddListener: (Note) -> Unit = {},
    val onNoteCancelListener: () -> Unit = {},
    val onNoteEditListener: (Note) -> Unit = {},
    val onNoteDeleteListener: (Note) -> Unit = {}
) : Parcelable

class NoteDialog : ViewBindingBottomSheet<DialogAddNoteBinding>() {

    companion object {
        const val TAG = "NoteDialog"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogAddNoteBinding
        get() = DialogAddNoteBinding::inflate

    val dialogArgs: NoteDialogArgs by navArgs()

    override fun setup(): Unit = with(binding) {

        val builder = dialogArgs.dialogBuilder

        editTitle.setText(builder.note.title)
        editDesc.setText(builder.note.description)

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
            val deleteNote = builder.note
            builder.onNoteDeleteListener.invoke(deleteNote)
            dismiss()
        }

        btnUpdate.setOnClickListener {
            val note = builder.note
            val updatedNote = note.copy(
                title = editTitle.text.toString(),
                description = editDesc.text.toString()
            )
            builder.onNoteEditListener.invoke(updatedNote)
            dismiss()
        }
    }
}
