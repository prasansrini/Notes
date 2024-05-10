package com.my.notes.feature_note.presentation.add_edit_note

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.my.notes.R
import com.my.notes.core.util.Constants.CONTENT_TEXT_FIELD
import com.my.notes.core.util.Constants.TITLE_TEXT_FIELD
import com.my.notes.feature_note.domain.model.Note
import com.my.notes.feature_note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
		navController: NavController, noteColor: Int, viewModel: AddEditNoteViewModel = hiltViewModel()
) {
	val titleState = viewModel.noteTitle.value
	val contentState = viewModel.noteContent.value

	val scaffoldState = remember {
		SnackbarHostState()
	}

	val noteBackgroundAnimatable = remember {
		Animatable(
			Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
		)
	}

	val scope = rememberCoroutineScope()

	LaunchedEffect(key1 = true) {
		viewModel.eventFlow.collectLatest { event ->
			when (event) {
				is AddEditNoteViewModel.UiEvent.ShowSnackBar -> {
					scaffoldState.showSnackbar(
						message = event.message,
						duration = SnackbarDuration.Short
					)
				}

				is AddEditNoteViewModel.UiEvent.SaveNote -> {
					navController.navigateUp()
				}
			}
		}
	}

	Scaffold(floatingActionButton = {
		FloatingActionButton(onClick = {
			viewModel.onEvent(AddEditNoteEvent.SaveNote)
		}) {
			Icon(
				imageVector = Icons.Default.Save,
				contentDescription = stringResource(R.string.save_note_floating_content_description)
			)
		}
	},
		snackbarHost = { SnackbarHost(hostState = scaffoldState) }) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.background(noteBackgroundAnimatable.value)
				.padding(16.dp)
		) {
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Icon(imageVector = Icons.Default.ArrowBackIosNew,
					contentDescription = stringResource(R.string.back_content_description),
					modifier = Modifier
						.size(40.dp)
						.padding(4.dp)
						.align(Alignment.CenterVertically)
						.clickable {
							navController.navigateUp()
							viewModel.onEvent(AddEditNoteEvent.SaveNote)
						})

				Note.noteColors.forEach { color ->
					val colorInt = color.toArgb()
					Box(modifier = Modifier
						.size(50.dp)
						.shadow(
							15.dp,
							CircleShape
						)
						.clip(CircleShape)
						.background(color)
						.border(
							width = 3.dp,
							color = if (viewModel.noteColor.value == colorInt) {
								Color.Black
							} else Color.Transparent,
							shape = CircleShape
						)
						.clickable {
							scope.launch {
								noteBackgroundAnimatable.animateTo(
									targetValue = Color(colorInt),
									animationSpec = tween(
										durationMillis = 500
									)
								)
							}
							viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
						})
				}
			}

			Spacer(Modifier.height(16.dp))

			TransparentHintTextField(
				text = titleState.text,
				hint = titleState.hint,
				onValueChange = {
					viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
				},
				onFocusChange = {
					viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
				},
				isHintVisible = titleState.isHintVisible,
				singleLine = true,
				textStyle = MaterialTheme.typography.headlineSmall,
				testTag = TITLE_TEXT_FIELD

			)

			Spacer(Modifier.height(16.dp))

			TransparentHintTextField(
				text = contentState.text,
				hint = contentState.hint,
				onValueChange = {
					viewModel.onEvent(AddEditNoteEvent.EnterContent(it))
				},
				onFocusChange = {
					viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
				},
				isHintVisible = contentState.isHintVisible,
				textStyle = MaterialTheme.typography.bodySmall,
				modifier = Modifier.fillMaxHeight(),
				testTag = CONTENT_TEXT_FIELD
			)
		}
	}
}
