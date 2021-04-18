package com.ch8n.taskie.ui.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch8n.taskie.data.model.Note
import com.ch8n.taskie.data.model.NoteType
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
    fun onTodoUpdate(todo: Note)
}

class NotesViewHolder(
    private val binding: ItemLayoutNoteBinding,
    private val noteListInteraction: NoteListInteraction
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(note: Note) = with(binding) {
        textNoteTitle.text = note.title
        textNoteDesc.text = note.description
        root.setOnLongClickListener {
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
        check.isChecked = (todo.type as NoteType.Todo).isCompleted
        textTodoTitle.text = todo.title
        textTodoDesc.text = todo.description
        root.setOnLongClickListener {
            noteListInteraction.onNoteEditClick(todo)
            true
        }
        root.setOnClickListener {
            noteListInteraction.onTodoUpdate(todo)
        }
    }

}