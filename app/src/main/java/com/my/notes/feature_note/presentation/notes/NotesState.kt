package com.my.notes.feature_note.presentation.notes

import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.domain.util.NoteOrder
import com.my.notes.feature_note.domain.util.OrderByType

data class NotesState(
	val notes: List<Note> = emptyList(),
	val noteOrder: NoteOrder = NoteOrder.Date(OrderByType.Descending),
	val isOrderSelectionVisible: Boolean = false
)
