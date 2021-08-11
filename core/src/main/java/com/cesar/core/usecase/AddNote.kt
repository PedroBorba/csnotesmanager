package com.cesar.core.usecase

import com.cesar.core.data.Note
import com.cesar.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}