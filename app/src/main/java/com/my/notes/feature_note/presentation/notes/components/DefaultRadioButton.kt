package com.my.notes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun DefaultRadioButton(
		text: String, selected: Boolean, onSelect: () -> Unit, modifier: Modifier = Modifier
) {
	Row(
		modifier = modifier,
		verticalAlignment = Alignment.CenterVertically
	) {
		RadioButton(selected = selected,
			onClick = onSelect,
			colors = RadioButtonDefaults.colors(
				selectedColor = MaterialTheme.colorScheme.primary,
				unselectedColor = MaterialTheme.colorScheme.onBackground
			),
			modifier = Modifier.semantics {
				contentDescription = text
			})

		Text(
			text = text,
			style = MaterialTheme.typography.bodySmall
		)
	}
}