package com.my.notes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.my.notes.R
import com.my.notes.feature_note.domain.util.NoteOrder
import com.my.notes.feature_note.domain.util.OrderByType

@Composable
fun OrderSection(
		modifier: Modifier = Modifier,
		noteOrder: NoteOrder = NoteOrder.Date(OrderByType.Descending),
		onOrderChange: (NoteOrder) -> Unit
) {
	Column(
		modifier = modifier
	) {
		VerticalDivider(
			color = LightGray,
			modifier = Modifier
				.height(1.dp)
				.fillMaxWidth()
				.padding(horizontal = 12.dp)
		)

		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			DefaultRadioButton(

				text = stringResource(R.string.order_title),
				selected = noteOrder is NoteOrder.Title,
				onSelect = { onOrderChange(NoteOrder.Title(orderType = noteOrder.orderByType)) }

			)

			Spacer(modifier = Modifier.width(8.dp))

			DefaultRadioButton(

				text = stringResource(R.string.order_date),
				selected = noteOrder is NoteOrder.Date,
				onSelect = { onOrderChange(NoteOrder.Date(orderType = noteOrder.orderByType)) }

			)

			Spacer(modifier = Modifier.width(8.dp))

			DefaultRadioButton(

				text = stringResource(R.string.order_color),
				selected = noteOrder is NoteOrder.Color,
				onSelect = { onOrderChange(NoteOrder.Color(orderType = noteOrder.orderByType)) }

			)
		}

		VerticalDivider(
			color = LightGray,
			modifier = Modifier
				.height(1.dp)
				.fillMaxWidth()
				.padding(horizontal = 12.dp)
		)

		Row(
			modifier = Modifier.fillMaxWidth()
		) {
			DefaultRadioButton(

				text = "Ascending",
				selected = noteOrder.orderByType is OrderByType.Ascending,
				onSelect = { onOrderChange(noteOrder.copy(OrderByType.Ascending)) }

			)

			Spacer(modifier = Modifier.width(8.dp))

			DefaultRadioButton(

				text = "Descending",
				selected = noteOrder.orderByType is OrderByType.Descending,
				onSelect = { onOrderChange(noteOrder.copy(OrderByType.Descending)) }

			)
		}

		VerticalDivider(
			color = LightGray,
			modifier = Modifier
				.height(1.dp)
				.fillMaxWidth()
				.padding(horizontal = 12.dp)
		)
	}
}