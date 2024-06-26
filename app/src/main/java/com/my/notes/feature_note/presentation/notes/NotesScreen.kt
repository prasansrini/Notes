package com.my.notes.feature_note.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.my.notes.R
import com.my.notes.core.util.Constants.ORDER_SECTION
import com.my.notes.feature_note.presentation.notes.components.NoteItem
import com.my.notes.feature_note.presentation.notes.components.OrderSection
import com.my.notes.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotesScreen(
		navController: NavController, viewModel: NotesViewModel = hiltViewModel()
) {
	val state = viewModel.state.value

	val scaffoldState = remember {
		SnackbarHostState()
	}

	val scope = rememberCoroutineScope()

	Scaffold(snackbarHost = { SnackbarHost(hostState = scaffoldState) },
		floatingActionButton = {
			FloatingActionButton(onClick = {
				navController.navigate(Screen.AddEditNoteScreen.route)
			}) {
				Icon(
					imageVector = Icons.Default.Add,
					contentDescription = stringResource(R.string.add_note_content_description),
				)
			}
		}) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Text(
					text = stringResource(R.string.notes_title),
					style = MaterialTheme.typography.headlineSmall
				)
				IconButton(onClick = {
					viewModel.onEvent(NotesEvent.ToggleOrderSection)
				}) {
					Icon(
						imageVector = Icons.Default.SortByAlpha,
						contentDescription = stringResource(R.string.sort)
					)
				}
			}
			AnimatedVisibility(
				visible = state.isOrderSelectionVisible,
				enter = fadeIn() + slideInVertically(),
				exit = fadeOut() + slideOutVertically()
			) {
				OrderSection(modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp)
					.testTag(ORDER_SECTION),
					noteOrder = state.noteOrder,
					onOrderChange = {
						viewModel.onEvent(NotesEvent.Order(it))
					})
			}
			Spacer(modifier = Modifier.height(16.dp))
			val snackMessage = stringResource(R.string.note_deleted)
			val snackUndoMessage = stringResource(R.string.note_deleted_undo)
			LazyColumn(
				Modifier.fillMaxSize()
			) {
				items(state.notes) { note ->
					NoteItem(note = note,
						modifier = Modifier
							.fillMaxWidth()
							.clickable {
								navController.navigate(
									Screen.AddEditNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}"
								)
							},
						onDeleteClick = {

							viewModel.onEvent(NotesEvent.Delete(note))
							scope.launch {
								val result = scaffoldState.showSnackbar(
									message = snackMessage,
									actionLabel = snackUndoMessage,
									duration = SnackbarDuration.Short
								)

								if (result == SnackbarResult.ActionPerformed) {
									viewModel.onEvent(NotesEvent.RestoreNote)
								}
							}
						})
					Spacer(modifier = Modifier.height(16.dp))
				}
			}
		}
	}
}
