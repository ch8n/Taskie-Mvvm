package com.ch8n.taskie.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentNotesBinding
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.notes.adapter.NoteListAdapter
import com.ch8n.taskie.ui.notes.adapter.NoteListInteraction
import com.ch8n.taskie.ui.notes.dialog.NoteDialogBuilder
import com.ch8n.taskie.ui.notes.dialog.NoteDialog
import com.google.android.material.snackbar.Snackbar

class NotesFragment : ViewBindingFragment<FragmentNotesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesBinding
        get() = FragmentNotesBinding::inflate

    private var notesAdapter: NoteListAdapter? = null
    private val notesViewModelFactory by lazy { Injector.noteViewModelFactory }
    private val notesViewModel by viewModels<NotesViewModel> { notesViewModelFactory }

    override fun setup(): Unit = with(binding) {

        textGreet.text = "All Your Notes..."

        listNotes.adapter = NoteListAdapter.newInstance(
            type = NoteType.Note,
            noteListInteraction = object : NoteListInteraction {
                override fun onNoteEditClick(note: Note) {
                    applyAddModifyBehaviour(note)
                }

                override fun onTodoEditClick(todo: Note) {}
                override fun onTodoUpdate(position: Int) {}
            }
        ).also {
            notesAdapter = it
        }

        notesViewModel.prompts.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            Snackbar.make(root, it, Snackbar.LENGTH_LONG).show()
        })

        notesViewModel.getNotes().observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            notesAdapter?.submitList(it)
        })

    }

    fun openCreateNoteDialog() {
        val noteDialog = NoteDialog()
        val noteDialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Note,
            actionEditOrDelete = false,
            onNoteAddListener = { newNote ->
                notesViewModel.addNote(newNote)
            }
        )
        noteDialog.setDialogBuilder(noteDialogBuilder)

        val fragment = childFragmentManager.findFragmentByTag(NoteDialog.TAG)
        if (fragment != null && fragment is NoteDialog) {
            fragment.dismiss()
        }
        noteDialog.show(childFragmentManager, NoteDialog.TAG)
    }

    private fun applyAddModifyBehaviour(note: Note) {
        val noteDialog = NoteDialog()
        noteDialog.modifyNote.value = note
        val noteDialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Note,
            actionEditOrDelete = true,
            onNoteDeleteListener = { deleteNode ->
                notesViewModel.deleteNote(deleteNode)
            },
            onNoteEditListener = { modifyNode ->
                notesViewModel.updateNote(modifyNode)
            }
        )
        noteDialog.setDialogBuilder(noteDialogBuilder)
        val fragment = childFragmentManager.findFragmentByTag(NoteDialog.TAG)
        if (fragment != null && fragment is NoteDialog) {
            fragment.dismiss()
        }
        noteDialog.show(childFragmentManager, NoteDialog.TAG)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        notesAdapter = null
    }
}