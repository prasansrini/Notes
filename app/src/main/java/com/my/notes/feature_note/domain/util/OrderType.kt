package com.my.notes.feature_note.domain.util

sealed class OrderByType {
	data object Ascending : OrderByType()
	data object Descending : OrderByType()
}