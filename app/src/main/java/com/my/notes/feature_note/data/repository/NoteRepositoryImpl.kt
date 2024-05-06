package com.my.notes.feature_note.data.repository

import com.my.notes.feature_note.data.data_source.NotesDao
import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
	private val notesDao: NotesDao
) : NoteRepository {

	override fun getAllNotes(): Flow<List<Note>> {
		return notesDao.getAllNotes()
	}

	override suspend fun getNoteById(id: Int): Note? {
		return notesDao.getNoteById(id)
	}

	override suspend fun insertNote(note: Note) {
		notesDao.insertNote(note)
	}

	override suspend fun deleteNote(note: Note) {
		notesDao.deleteNote(note)
	}
}