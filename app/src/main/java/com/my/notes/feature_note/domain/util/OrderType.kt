package com.my.notes.feature_note.domain.util

sealed class OrderByType {
	object Ascending : OrderByType()
	object Descending : OrderByType()
}