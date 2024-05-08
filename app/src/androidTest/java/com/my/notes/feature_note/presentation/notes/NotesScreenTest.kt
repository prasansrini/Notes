package com.my.notes.feature_note.presentation.notes

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.my.notes.R
import com.my.notes.core.util.Constants.ORDER_SECTION
import com.my.notes.feature_note.di.AppModule
import com.my.notes.feature_note.presentation.MainActivity
import com.my.notes.feature_note.presentation.util.Screen
import com.my.notes.feature_note.ui.theme.NotesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesScreenTest {
	@get:Rule(order = 0)
	val hiltRule = HiltAndroidRule(this)

	@get:Rule(order = 1)
	val composeRule = createAndroidComposeRule<MainActivity>()

	@Before
	fun setup() {
		hiltRule.inject()

		composeRule.activity.setContent {
			val navController = rememberNavController()

			NotesTheme {
				NavHost(
					navController = navController,
					startDestination = Screen.NoteScreen.route
				) {
					composable(route = Screen.NoteScreen.route) {
						NotesScreen(navController = navController)
					}
				}
			}
		}
	}

	@Test
	fun clickToggleOrderSection_isVisible() {
		val context = ApplicationProvider.getApplicationContext<Context>()

		composeRule
			.onNodeWithTag(ORDER_SECTION)
			.assertDoesNotExist()

		composeRule
			.onNodeWithContentDescription(context?.getString(R.string.sort)!!)
			.performClick()

		composeRule
			.onNodeWithTag(ORDER_SECTION)
			.assertIsDisplayed()
	}

	@Test
	fun clickToggleOrderSection_isNotVisible() {
		val context = ApplicationProvider.getApplicationContext<Context>()

		composeRule
			.onNodeWithTag(ORDER_SECTION)
			.assertDoesNotExist()

		composeRule
			.onNodeWithContentDescription(context?.getString(R.string.sort)!!)
			.performClick()
			.performClick()

		composeRule
			.onNodeWithTag(ORDER_SECTION)
			.assertDoesNotExist()
	}

	@Test
	fun clickToggleOrderSection_isSubMenuVisible() {
		val context = ApplicationProvider.getApplicationContext<Context>()

		composeRule
			.onNodeWithContentDescription(context?.getString(R.string.sort)!!)
			.performClick()

		composeRule
			.onNodeWithText(context.getString(R.string.order_title))
			.assertIsDisplayed()

		composeRule
			.onNodeWithText(context.getString(R.string.order_date))
			.assertIsDisplayed()

		composeRule
			.onNodeWithText(context.getString(R.string.order_color))
			.assertIsDisplayed()
	}

	@Test
	fun addButton_isVisible() {
		val context = ApplicationProvider.getApplicationContext<Context>()

		composeRule
			.onNodeWithContentDescription(context?.getString(R.string.add_note_content_description)!!)
			.assertIsDisplayed()
	}
}