package com.my.notes.feature_note.domain.use_case

import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.domain.repository.NoteRepository
import com.my.notes.feature_note.domain.util.NoteOrder
import com.my.notes.feature_note.domain.util.OrderByType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNoteListUseCase(
	private val repository: NoteRepository
) {
	operator fun invoke(
		noteOrder: NoteOrder = NoteOrder.Date(OrderByType.Descending)
	): Flow<List<Note>> {
		return repository
			.getAllNotes()
			.map { notes ->
				when (noteOrder.orderByType) {
					is OrderByType.Ascending -> {
						when (noteOrder) {
							is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
							is NoteOrder.Date -> notes.sortedBy { it.timestamp }
							is NoteOrder.Color -> notes.sortedBy { it.color }
						}
					}

					is OrderByType.Descending -> {
						when (noteOrder) {
							is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
							is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
							is NoteOrder.Color -> notes.sortedByDescending { it.color }
						}
					}
				}
			}
	}
}