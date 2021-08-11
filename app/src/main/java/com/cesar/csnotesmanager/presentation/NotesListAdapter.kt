package com.cesar.csnotesmanager.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cesar.core.data.Note
import com.cesar.csnotesmanager.databinding.ItemNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesListAdapter(var notes: ArrayList<Note>, val actions: ListAction) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemBinding: ItemNoteBinding): RecyclerView.ViewHolder(itemBinding.root) {

        private val layout = itemBinding.noteLayout
        private val noteTitle = itemBinding.noteTitle
        private val noteContent = itemBinding.noteContent
        private val noteDate = itemBinding.date
        private val noteWords = itemBinding.wordCount

        fun bind(note: Note){
            noteTitle.text = note.title
            noteContent.text = note.content
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last Updated: ${sdf.format(resultDate)}"

            layout.setOnClickListener{
                actions.onClick(note.id)
            }

            noteWords.text = "Words: ${note.wordCount}"
        }
    }

    fun updateNotes(newNotes: List<Note>){
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemBinding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size
}