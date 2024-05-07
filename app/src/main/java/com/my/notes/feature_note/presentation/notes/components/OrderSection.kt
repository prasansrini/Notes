package com.my.notes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.notes.feature_note.domain.util.NoteOrder
import com.my.notes.feature_note.domain.util.OrderByType

@Composable
fun OrderSection(
		modifier: Modifier = Modifier, noteOrder: NoteOrder = NoteOrder.Date(OrderByType.Descending),
	onOrderChange: (NoteOrder) -> Unit
) {
	Column(
		modifier = modifier
	) {
		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			DefaultRadioButton(text = "Title",
				selected = noteOrder is NoteOrder.Title,
				onCheck = { onOrderChange(NoteOrder.Title(orderType = noteOrder.orderByType)) })
		}
		Spacer(modifier = Modifier.width(8.dp))
		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			DefaultRadioButton(text = "Date",
				selected = noteOrder is NoteOrder.Date,
				onCheck = { onOrderChange(NoteOrder.Title(orderType = noteOrder.orderByType)) })
		}
		Spacer(modifier = Modifier.width(8.dp))
		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			DefaultRadioButton(text = "Color",
				selected = noteOrder is NoteOrder.Color,
				onCheck = { onOrderChange(NoteOrder.Title(orderType = noteOrder.orderByType)) })
		}
		Spacer(modifier = Modifier.height(16.dp))
		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			Row(
				modifier = Modifier.fillMaxWidth()
			) {
				DefaultRadioButton(text = "Ascending",
					selected = noteOrder.orderByType is OrderByType.Ascending,
					onCheck = { onOrderChange(noteOrder.copy(OrderByType.Ascending)) })
			}
			Spacer(modifier = Modifier.width(8.dp))
			Row(
				modifier = Modifier.fillMaxWidth()
			) {
				DefaultRadioButton(text = "Ascending",
					selected = noteOrder.orderByType is OrderByType.Descending,
					onCheck = { onOrderChange(noteOrder.copy(OrderByType.Descending)) })
			}
		}
	}
}