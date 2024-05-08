package com.my.notes.feature_note.domain.repository

import com.my.notes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : NoteRepository {

	private val noteList = mutableListOf<Note>()

	override fun getAllNotes(): Flow<List<Note>> {
		return flow {
			emit(noteList)
		}
	}

	override suspend fun getNoteById(id: Int): Note? {
		return noteList.find {
			it.id == id
		}
	}

	override suspend fun insertNote(note: Note) {
		noteList.add(note)
	}

	override suspend fun deleteNote(note: Note) {
		noteList.remove(note)
	}
}