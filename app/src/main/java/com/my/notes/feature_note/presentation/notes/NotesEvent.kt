package com.my.notes.feature_note.presentation.notes

import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
	data class Order(val noteOrder: NoteOrder) : NotesEvent()
	data class Delete(val note: Note) : NotesEvent()
	data object RestoreNote : NotesEvent()
	data object ToggleOrderSection : NotesEvent()
}