package com.ch8n.taskie.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ch8n.taskie.ActivityScopedViewModel
import com.ch8n.taskie.R
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.ViewBindingFragment
import com.ch8n.taskie.databinding.FragmentNotesBinding
import com.ch8n.taskie.di.Injector
import com.ch8n.taskie.ui.home.HomeFragmentDirections
import com.ch8n.taskie.ui.notes.adapter.NoteListAdapter
import com.ch8n.taskie.ui.notes.adapter.NoteListInteraction
import com.ch8n.taskie.ui.notes.dialog.NoteDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow


class TaskFragment : ViewBindingFragment<FragmentNotesBinding>() {

    companion object {
        const val TAG = "TaskFragment"
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNotesBinding
        get() = FragmentNotesBinding::inflate

    private var taskAdapter: NoteListAdapter? = null
    private val taskViewModelFactory by lazy { Injector.taskViewModelFactory }
    private val taskViewModel by viewModels<TaskViewModel> { taskViewModelFactory }
    private val taskiePrefs by lazy { Injector.taskiePrefs }
    private val sharedViewModel: HomeTaskSharedViewModel by activityViewModels<ActivityScopedViewModel>()

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

        lifecycleScope.launchWhenResumed {
            sharedViewModel.createNewNote.receiveAsFlow().collect {
                openCreateTaskDialog(it)
            }
        }
    }

    fun openCreateTaskDialog(note: Note) {
        val dialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Todo,
            note = note,
            actionEditOrDelete = false,
            onNoteAddListener = { newTask ->
                taskViewModel.addTask(newTask)
            }
        )

        findNavController().navigate(
            R.id.noteDialog,
            bundleOf("dialogBuilder" to dialogBuilder)
        )
    }

    private fun applyAddModifyBehaviour(task: Note) {
        val dialogBuilder = NoteDialogBuilder(
            noteType = NoteType.Note,
            actionEditOrDelete = true,
            onNoteDeleteListener = { deleteTask ->
                taskViewModel.deleteTask(deleteTask)
            },
            note = task,
            onNoteEditListener = { modifyTask ->
                taskViewModel.updateTask(modifyTask)
            }
        )
        findNavController().navigate(
            R.id.noteDialog,
            bundleOf("dialogBuilder" to dialogBuilder)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        taskAdapter = null
    }
}