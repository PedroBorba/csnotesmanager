package com.cesar.core.usecase

import com.cesar.core.data.Note
import com.cesar.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}