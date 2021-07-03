package com.ch8n.taskie.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentNotesBinding
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.notes.adapter.NoteListAdapter
import com.ch8n.taskie.ui.notes.adapter.NoteListInteraction
import com.ch8n.taskie.ui.notes.dialog.NoteDialog
import com.ch8n.taskie.ui.notes.dialog.NoteDialogBuilder
import com.google.android.material.snackbar.Snackbar

class NotesFragment : ViewBindingFragment<FragmentNotesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesBinding
        get() = FragmentNotesBinding::inflate

    private var notesAdapter: NoteListAdapter? = null
    private val notesViewModelFactory by lazy { Injector.noteViewModelFactory }
    private val notesViewModel by viewModels<NotesViewModel> { notesViewModelFactory }

    private val router by lazy { findNavController() }

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

    fun openCreateNoteDialog(note: Note) {
        val noteDialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Note,
            actionEditOrDelete = false,
            note = note,
            onNoteAddListener = { newNote ->
                notesViewModel.addNote(newNote)
            }
        )
        val gotoNoteDialog =
            NotesFragmentDirections.actionNotesFragmentToNoteDialog(noteDialogBuilder)
        router.navigate(gotoNoteDialog)
    }

    private fun applyAddModifyBehaviour(note: Note) {
        val noteDialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Note,
            actionEditOrDelete = true,
            note = note,
            onNoteDeleteListener = { deleteNode ->
                notesViewModel.deleteNote(deleteNode)
            },
            onNoteEditListener = { modifyNode ->
                notesViewModel.updateNote(modifyNode)
            }
        )
        val gotoNoteDialog = NotesFragmentDirections
                .actionNotesFragmentToNoteDialog(noteDialogBuilder)
        router.navigate(gotoNoteDialog)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        notesAdapter = null
    }
}