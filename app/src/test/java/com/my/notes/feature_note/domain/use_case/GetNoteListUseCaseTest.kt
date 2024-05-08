package com.my.notes.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.domain.repository.FakeRepository
import com.my.notes.feature_note.domain.util.NoteOrder
import com.my.notes.feature_note.domain.util.OrderByType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNoteListUseCaseTest {
	private lateinit var getNoteList: GetNoteListUseCase
	private lateinit var fakeRepository: FakeRepository

	@Before
	fun setUp() {
		fakeRepository = FakeRepository()
		getNoteList = GetNoteListUseCase(fakeRepository)

		val notesToInsert = mutableListOf<Note>()

		('a'..'z').forEachIndexed { index, c ->
			notesToInsert.add(
				Note(
					title = c.toString(),
					content = c.toString(),
					timestamp = index.toLong(),
					color = index
				)
			)
		}

		notesToInsert.shuffle()

		runBlocking {
			notesToInsert.forEach {
				fakeRepository.insertNote(it)
			}
		}
	}

	@Test
	fun `Order notes by title ascending, correct order`() = runBlocking {
		val notes = getNoteList(NoteOrder.Title(OrderByType.Ascending)).first()

		for (i in 0..notes.size - 2) {
			assertThat(notes[i].title).isLessThan(notes[i + 1].title)
		}
	}

	@Test
	fun `Order notes by title descending, correct order`() = runBlocking {
		val notes = getNoteList(NoteOrder.Title(OrderByType.Descending)).first()

		for (i in 0..notes.size - 2) {
			assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
		}
	}

	@Test
	fun `Order notes by date ascending, correct order`() = runBlocking {
		val notes = getNoteList(NoteOrder.Date(OrderByType.Ascending)).first()

		for (i in 0..notes.size - 2) {
			assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)
		}
	}

	@Test
	fun `Order notes by date descending, correct order`() = runBlocking {
		val notes = getNoteList(NoteOrder.Date(OrderByType.Descending)).first()

		for (i in 0..notes.size - 2) {
			assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
		}
	}

	@Test
	fun `Order notes by color ascending, correct order`() = runBlocking {
		val notes = getNoteList(NoteOrder.Color(OrderByType.Ascending)).first()

		for (i in 0..notes.size - 2) {
			assertThat(notes[i].color).isLessThan(notes[i + 1].color)
		}
	}

	@Test
	fun `Order notes by color descending, correct order`() = runBlocking {
		val notes = getNoteList(NoteOrder.Color(OrderByType.Descending)).first()

		for (i in 0..notes.size - 2) {
			assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
		}
	}
}