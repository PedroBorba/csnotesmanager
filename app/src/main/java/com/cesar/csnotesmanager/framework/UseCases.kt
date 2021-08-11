package com.cesar.csnotesmanager.framework

import com.cesar.core.usecase.*

data class UseCases (
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote,
    val getWordCount: GetWordCount
)