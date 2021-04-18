package com.ch8n.taskie.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentNotesBinding
import com.ch8n.taskie.ui.notes.adapter.NoteListAdapter
import com.ch8n.taskie.ui.notes.adapter.NoteListInteraction
import com.ch8n.taskie.ui.notes.dialog.NoteDialog

class TaskFragment : ViewBindingFragment<FragmentNotesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesBinding
        get() = FragmentNotesBinding::inflate

    private var todoAdapter: NoteListAdapter? = null

    override fun setup(): Unit = with(binding) {

        applyAddTaskBehaviour()

        listNotes.adapter = NoteListAdapter.newInstance(
            type = NoteType.Todo(false),
            noteListInteraction = object : NoteListInteraction {
                override fun onNoteEditClick(note: Note) {}

                override fun onTodoEditClick(todo: Note) {

                }

                override fun onTodoUpdate(todo: Note) {

                }
            }
        ).also {
            todoAdapter = it
        }

        todoAdapter?.submitList(
            mutableListOf<Note>().also { todos ->
                repeat(20) {
                    todos.add(Note.fakeTodo())
                }
            }
        )
    }

    private fun applyAddTaskBehaviour(){
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
        todoAdapter = null
    }
}