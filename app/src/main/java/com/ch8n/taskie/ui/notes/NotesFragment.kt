package com.ch8n.taskie.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentNotesBinding
import com.ch8n.taskie.ui.notes.adapter.NoteListAdapter
import com.ch8n.taskie.ui.notes.adapter.NoteListInteraction
import com.ch8n.taskie.ui.notes.dialog.NoteDialog

class NotesFragment : ViewBindingFragment<FragmentNotesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesBinding
        get() = FragmentNotesBinding::inflate

    private var notesAdapter: NoteListAdapter? = null

    override fun setup(): Unit = with(binding) {

        applyAddNoteBehaviour()

        listNotes.adapter = NoteListAdapter.newInstance(
            type = NoteType.Note,
            noteListInteraction = object : NoteListInteraction {
                override fun onNoteEditClick(note: Note) {

                }

                override fun onTodoEditClick(todo: Note) {}
                override fun onTodoUpdate(todo: Note) {}
            }
        ).also {
            notesAdapter = it
        }

        notesAdapter?.submitList(
            mutableListOf<Note>().also { note ->
                repeat(20) {
                    note.add(Note.fakeNote())
                }
                note.sortBy { it.createdAt }
            }
        )

    }

    private fun applyAddNoteBehaviour() {
        binding.btnAdd.setOnClickListener {
            val fragment = childFragmentManager.findFragmentByTag(NoteDialog.TAG)
            if (fragment != null && fragment is NoteDialog) {
                fragment.dismiss()
            }
            val noteDialog = NoteDialog()
            noteDialog.show(childFragmentManager, NoteDialog.TAG)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        notesAdapter = null
    }
}