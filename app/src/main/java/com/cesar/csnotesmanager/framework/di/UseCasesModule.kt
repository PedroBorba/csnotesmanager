package com.cesar.csnotesmanager.framework.di

import com.cesar.core.repository.NoteRepository
import com.cesar.core.usecase.*
import com.cesar.csnotesmanager.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun getUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount()
    )
}