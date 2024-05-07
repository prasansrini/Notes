package com.my.notes.feature_note.domain.util

sealed class NoteOrder(val orderByType: OrderByType) {
	class Title(orderType: OrderByType) : NoteOrder(orderType)
	class Date(orderType: OrderByType) : NoteOrder(orderType)
	class Color(orderType: OrderByType) : NoteOrder(orderType)

	fun copy(orderType: OrderByType): NoteOrder {
		return when (this) {
			is Title -> Title(orderType)
			is Date -> Date(orderType)
			is Color -> Color(orderType)
		}
	}
}
