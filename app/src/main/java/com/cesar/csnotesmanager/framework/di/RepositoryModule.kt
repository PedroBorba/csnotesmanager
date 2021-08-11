package com.cesar.csnotesmanager.framework.di

import android.app.Application
import com.cesar.core.repository.NoteRepository
import com.cesar.csnotesmanager.framework.RoomNoteDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(app: Application) = NoteRepository(RoomNoteDataSource(app))
}