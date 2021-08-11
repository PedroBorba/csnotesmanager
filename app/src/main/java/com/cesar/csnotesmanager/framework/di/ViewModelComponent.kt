package com.cesar.csnotesmanager.framework.di

import com.cesar.csnotesmanager.framework.viewmodel.ListViewModel
import com.cesar.csnotesmanager.framework.viewmodel.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}