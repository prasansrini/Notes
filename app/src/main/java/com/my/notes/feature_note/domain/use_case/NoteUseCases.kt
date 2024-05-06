package com.my.notes.feature_note.domain.use_case

data class NoteUseCases(
	val getNotes: GetNoteListUseCase, val deleteNote: DeleteNotesUseCase, val addNote: AddNote
)
