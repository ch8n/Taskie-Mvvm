package com.ch8n.taskie.ui.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
import com.ch8n.taskie.data.utils.showStrikeThrough
import com.ch8n.taskie.databinding.ItemLayoutNoteBinding
import com.ch8n.taskie.databinding.ItemLayoutTodoBinding

class NoteListAdapter private constructor(
    diffUtil: DiffUtil.ItemCallback<Note>,
    private val type: NoteType,
    private val noteListInteraction: NoteListInteraction
) :
    ListAdapter<Note, RecyclerView.ViewHolder>(diffUtil) {

    fun getItemAt(position: Int) = getItem(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (type) {
            is NoteType.Note -> NotesViewHolder(
                ItemLayoutNoteBinding.inflate(LayoutInflater.from(parent.context)),
                noteListInteraction
            )
            is NoteType.Todo -> TodoViewHolder(
                ItemLayoutTodoBinding.inflate(LayoutInflater.from(parent.context)),
                noteListInteraction
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NotesViewHolder -> holder.onBind(getItemAt(position))
            is TodoViewHolder -> holder.onBind(getItemAt(position))
        }
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
                oldItem == newItem
        }

        fun newInstance(type: NoteType, noteListInteraction: NoteListInteraction) =
            NoteListAdapter(DIFF_CALLBACK, type, noteListInteraction)
    }
}

interface NoteListInteraction {
    fun onNoteEditClick(note: Note)
    fun onTodoEditClick(todo: Note)
    fun onTodoUpdate(position: Int)
}

class NotesViewHolder(
    private val binding: ItemLayoutNoteBinding,
    private val noteListInteraction: NoteListInteraction
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(note: Note) = with(binding) {
        textNoteTitle.text = note.title
        textNoteDesc.text = note.description
        // TODO root click not working
        textNoteTitle.setOnLongClickListener {
            noteListInteraction.onNoteEditClick(note)
            true
        }
        textNoteDesc.setOnLongClickListener {
            noteListInteraction.onNoteEditClick(note)
            true
        }
    }
}

class TodoViewHolder(
    private val binding: ItemLayoutTodoBinding,
    private val noteListInteraction: NoteListInteraction
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(todo: Note) = with(binding) {
        check.isEnabled = false
        check.isChecked = todo.isCompleted
        if (check.isChecked) {
            textTodoTitle.showStrikeThrough(true)
            textTodoDesc.showStrikeThrough(true)
        }
        textTodoTitle.text = todo.title
        textTodoDesc.text = todo.description

        //TODO fix root click
        textTodoTitle.setOnLongClickListener {
            noteListInteraction.onTodoEditClick(todo)
            true
        }
        textTodoDesc.setOnLongClickListener {
            noteListInteraction.onTodoEditClick(todo)
            true
        }

        //TODO fix root click
        textTodoTitle.setOnClickListener {
            noteListInteraction.onTodoUpdate(adapterPosition)
        }
        textTodoDesc.setOnClickListener {
            noteListInteraction.onTodoUpdate(adapterPosition)
        }
    }

}