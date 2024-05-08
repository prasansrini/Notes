package com.my.notes.feature_note.di

import android.app.Application
import androidx.room.Room
import com.my.notes.feature_note.data.data_source.NoteDatabase
import com.my.notes.feature_note.data.repository.NoteRepositoryImpl
import com.my.notes.feature_note.domain.repository.NoteRepository
import com.my.notes.feature_note.domain.use_case.AddNote
import com.my.notes.feature_note.domain.use_case.DeleteNotesUseCase
import com.my.notes.feature_note.domain.use_case.GetNoteListUseCase
import com.my.notes.feature_note.domain.use_case.GetNoteUseCase
import com.my.notes.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
	@Provides
	@Singleton
	fun provideNoteDatabase(app: Application): NoteDatabase {
		return Room
			.inMemoryDatabaseBuilder(
				app,
				NoteDatabase::class.java
			)
			.build()
	}

	@Provides
	@Singleton
	fun providesNoteRepository(
			database: NoteDatabase
	): NoteRepository {
		return NoteRepositoryImpl(database.noteDao)
	}

	@Provides
	@Singleton
	fun providesUseCases(repository: NoteRepository): NoteUseCases {
		return NoteUseCases(
			getNotes = GetNoteListUseCase(repository),
			deleteNote = DeleteNotesUseCase(repository),
			addNote = AddNote(repository),
			getNote = GetNoteUseCase(repository)
		)
	}
}