package com.my.notes.feature_note.presentation.util

sealed class Screen(val route: String) {
	public object NoteScreen : Screen("notes_screen")
	public object AddEditNoteScreen : Screen("add_edit_note_screen")
}
