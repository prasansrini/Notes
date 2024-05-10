package com.my.notes.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.my.notes.R
import com.my.notes.core.util.Constants.CONTENT_TEXT_FIELD
import com.my.notes.core.util.Constants.TITLE_TEXT_FIELD
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

	private val TEST_TITLE = "test-title"
	private val TEST_NEW = "new-"
	private val TEST_NEW_TITLE = "new-test-title"
	private val TEST_CONTENT = "test-content"

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

		composeRule
			.onNodeWithTag(TITLE_TEXT_FIELD)
			.performTextInput(TEST_TITLE)

		composeRule
			.onNodeWithTag(CONTENT_TEXT_FIELD)
			.performTextInput(TEST_CONTENT)

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.save_note_floating_content_description))
			.performClick()

		composeRule
			.onNodeWithText(TEST_TITLE)
			.assertIsDisplayed()

		composeRule
			.onNodeWithText(TEST_TITLE)
			.performClick()

		composeRule
			.onNodeWithTag(TITLE_TEXT_FIELD)
			.assertTextEquals(TEST_TITLE)

		composeRule
			.onNodeWithTag(CONTENT_TEXT_FIELD)
			.assertTextEquals(TEST_CONTENT)

		composeRule
			.onNodeWithTag(TITLE_TEXT_FIELD)
			.performTextInput(TEST_NEW)

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.save_note_floating_content_description))
			.performClick()

		composeRule
			.onNodeWithText(TEST_NEW_TITLE)
			.assertIsDisplayed()
	}
}
