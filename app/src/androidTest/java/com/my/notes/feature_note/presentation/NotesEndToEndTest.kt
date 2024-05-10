package com.my.notes.feature_note.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
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
import com.my.notes.core.util.Constants.NOTE_ITEM
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

private const val TEST_TITLE = "test-title"
private const val TEST_NEW = "new-"
private const val TEST_NEW_TITLE = "new-test-title"
private const val TEST_CONTENT = "test-content"

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
	fun saveNewNote() {
		addNote(
			TEST_TITLE,
			TEST_CONTENT
		)

		composeRule
			.onNodeWithText(TEST_TITLE)
			.assertIsDisplayed()
	}

	@Test
	fun editNotes() {

		addNote(
			TEST_TITLE,
			TEST_CONTENT
		)

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

	@Test
	fun saveNewNotes_orderByTitleDescending() {
		val size = 3

		for (i in 1..size) {
			addNote(
				i.toString(),
				i.toString()
			)
		}

		for (i in 1..size) {
			composeRule
				.onNodeWithText(i.toString())
				.assertIsDisplayed()
		}

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.sort))
			.assertIsDisplayed()
			.performClick()

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.order_title_radio_content_description))
			.assertIsDisplayed()
			.performClick()

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.radio_text_descending))
			.assertIsDisplayed()
			.performClick()

		for (i in 1..size) {
			composeRule.onAllNodesWithTag(NOTE_ITEM)[i - 1].assertTextContains((size - i + 1).toString())
		}
	}

	@Test
	fun saveNewNotes_orderByTitleAscending() {
		val size = 3

		for (i in 1..size) {
			addNote(
				i.toString(),
				i.toString()
			)
		}

		for (i in 1..size) {
			composeRule
				.onNodeWithText(i.toString())
				.assertIsDisplayed()
		}

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.sort))
			.assertIsDisplayed()
			.performClick()

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.order_title_radio_content_description))
			.assertIsDisplayed()
			.performClick()

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.radio_text_ascending))
			.assertIsDisplayed()
			.performClick()

		for (i in 1..size) {
			composeRule.onAllNodesWithTag(NOTE_ITEM)[i - 1].assertTextContains(i.toString())
		}
	}

	private fun addNote(textInputTitle: String, textInputContent: String) {
		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.add_note_content_description))
			.performClick()

		composeRule
			.onNodeWithTag(TITLE_TEXT_FIELD)
			.performTextInput(textInputTitle)

		composeRule
			.onNodeWithTag(CONTENT_TEXT_FIELD)
			.performTextInput(textInputContent)

		composeRule
			.onNodeWithContentDescription(TEST_CONTEXT.getString(R.string.save_note_floating_content_description))
			.performClick()
	}
}
