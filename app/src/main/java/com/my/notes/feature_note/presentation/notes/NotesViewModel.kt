package com.my.notes.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.domain.use_case.NoteUseCases
import com.my.notes.feature_note.domain.util.NoteOrder
import com.my.notes.feature_note.domain.util.OrderByType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class NotesViewModel(
	private val notesUseCases: NoteUseCases
) : ViewModel() {

	private val _state = mutableStateOf(NotesState())
	val state: State<NotesState> = _state

	private var recentlyDeletedNote: Note? = null

	private var getNotesJob: Job? = null

	init {
		getNotes(NoteOrder.Date(OrderByType.Descending))
	}

	fun onEvent(event: NotesEvent) {
		when (event) {
			is NotesEvent.Order -> {
				if (state.value.noteOrder::class == event.noteOrder::class && state.value.noteOrder.orderByType == event.noteOrder.orderByType) {
					return
				}


			}

			is NotesEvent.Delete -> {
				viewModelScope.launch {
					notesUseCases.deleteNote(event.note)
					recentlyDeletedNote = event.note
				}
			}

			is NotesEvent.RestoreNote -> {
				viewModelScope.launch {
					notesUseCases.addNote(recentlyDeletedNote ?: return@launch)
					recentlyDeletedNote = null
				}
			}

			is NotesEvent.ToggleOrderSection -> {
				_state.value = state.value.copy(
					isOrderSelectionVisible = !state.value.isOrderSelectionVisible
				)
			}
		}
	}

	private fun getNotes(noteOrder: NoteOrder) {
		getNotesJob?.cancel()
		getNotesJob = notesUseCases
			.getNotes(noteOrder)
			.onEach { notes ->
				_state.value = state.value.copy(
					notes = notes
				)
			}
			.launchIn(viewModelScope)
	}
}