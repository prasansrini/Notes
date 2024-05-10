package com.my.notes.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.my.notes.R
import com.my.notes.feature_note.di.AppModule
import com.my.notes.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.my.notes.feature_note.presentation.notes.NotesScreen
import com.my.notes.feature_note.presentation.util.Screen
import com.my.notes.feature_note.ui.theme.NotesTheme
import com.my.notes.util.ContextTestUtil.TEST_CONTEXT
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {
	@get:Rule(order = 0)
	val hiltRule = HiltAndroidRule(this)

	@get:Rule(order = 1)
	val composeRule = createAndroidComposeRule<MainActivity>()

	@Before
	fun setup() {
		hiltRule.inject()

		composeRule.activity.setContent {
			NotesTheme {
				val navController = rememberNavController()

				NavHost(
					navController = navController,
					startDestination = Screen.NoteScreen.route
				) {
					composable(route = Screen.NoteScreen.route) {
						NotesScreen(navController = navController)
					}
					composable(
						route = Screen.AddEditNoteScreen.route + "?noteId={noteId}&noteColor={noteColor}",
						arguments = listOf(navArgument(
							name = "noteId"
						) {
							type = NavType.IntType
							defaultValue = -1
						},
							navArgument(
								name = "noteColor"
							) {
								type = NavType.IntType
								defaultValue = -1
							})
					) {
						val color = it.arguments?.getInt("noteColor")
								?: -1
						AddEditNoteScreen(
							navController = navController,
							noteColor = color
						)
					}
				}
			}
		}
	}

	@Test
	fun saveNewNote_editAfterwards() {
		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.add_note_content_description))
			.performClick()


	}
}
