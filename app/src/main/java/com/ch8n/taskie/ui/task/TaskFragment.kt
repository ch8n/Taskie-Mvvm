package com.ch8n.taskie.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
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


class TaskFragment : ViewBindingFragment<FragmentNotesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesBinding
        get() = FragmentNotesBinding::inflate

    private var taskAdapter: NoteListAdapter? = null
    private val taskViewModel by lazy { Injector.taskVM }
    private val taskiePrefs by lazy { Injector.taskiePrefs }

    override fun setup(): Unit = with(binding) {

        textGreet.text = "Today's Task ${taskiePrefs.userName.split(" ").first()}!"

        listNotes.adapter = NoteListAdapter.newInstance(
            type = NoteType.Todo,
            noteListInteraction = object : NoteListInteraction {
                override fun onNoteEditClick(note: Note) {
                    throw IllegalStateException("don't wire Note flow in Task")
                }

                override fun onTodoEditClick(task: Note) {
                    applyAddModifyBehaviour(task)
                }

                override fun onTodoUpdate(position: Int) {
                    val task = taskAdapter?.getItemAt(position) ?: return
                    val updatedTask = task.copy(isCompleted = !task.isCompleted)
                    taskViewModel.updateTask(updatedTask)
                }
            }
        ).also {
            taskAdapter = it
        }

        taskViewModel.prompts.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            Snackbar.make(root, it, Snackbar.LENGTH_LONG).show()
        })

        taskViewModel.getTask().observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            taskAdapter?.submitList(it)
        })

    }

    fun openCreateTaskDialog() {
        val taskCreateDialog = NoteDialog()
        val dialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Todo,
            actionEditOrDelete = false,
            onNoteAddListener = { newTask ->
                taskViewModel.addTask(newTask)
            }
        )

        taskCreateDialog.setDialogBuilder(dialogBuilder)

        val fragment = childFragmentManager.findFragmentByTag(NoteDialog.TAG)
        if (fragment != null && fragment is NoteDialog) {
            fragment.dismiss()
        }
        taskCreateDialog.show(childFragmentManager, NoteDialog.TAG)
    }


    private fun applyAddModifyBehaviour(task: Note) {
        val taskEditDialog = NoteDialog()
        taskEditDialog.modifyNote.value = task

        val dialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Note,
            actionEditOrDelete = true,
            onNoteDeleteListener = { deleteTask ->
                taskViewModel.deleteTask(deleteTask)
            },
            onNoteEditListener = { modifyTask ->
                taskViewModel.updateTask(modifyTask)
            }
        )
        taskEditDialog.setDialogBuilder(dialogBuilder)
        val fragment = childFragmentManager.findFragmentByTag(NoteDialog.TAG)
        if (fragment != null && fragment is NoteDialog) {
            fragment.dismiss()
        }
        taskEditDialog.show(childFragmentManager, NoteDialog.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        taskAdapter = null
    }
}