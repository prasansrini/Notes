package com.my.notes.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.domain.repository.FakeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddNoteTest {
	private lateinit var addNote: AddNote
	private lateinit var fakeRepository: FakeRepository

	@Before
	fun setUp() {
		fakeRepository = FakeRepository()
		addNote = AddNote(fakeRepository)
	}

	@Test
	fun `Add a note, not null note`() {
		val id = 1

		runBlocking {
			fakeRepository.insertNote(
				Note(
					title = "a",
					content = "a",
					timestamp = 1,
					color = 1,
					id = id
				)
			)
		}

		runBlocking {
			assertThat(fakeRepository.getNoteById(id)).isNotNull()
		}
	}

	@Test
	fun `Add a note with empty title, throws @InvalidNoteException`() {
		val id = 1

		runBlocking {
			fakeRepository.insertNote(
				Note(
					title = "",
					content = "a",
					timestamp = 1,
					color = 1,
					id = id
				)
			)
		}

		runBlocking {
			assertThat(fakeRepository.getNoteById(id)).isNotNull()
		}
	}
}