package com.cesar.csnotesmanager.framework

import android.content.Context
import com.cesar.core.data.Note
import com.cesar.core.repository.NoteDataSource
import com.cesar.csnotesmanager.framework.db.DatabaseService
import com.cesar.csnotesmanager.framework.db.NoteEntity

class RoomNoteDataSource(context: Context): NoteDataSource {

    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long) = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll() = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}