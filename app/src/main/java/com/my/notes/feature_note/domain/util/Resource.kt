package com.my.notes.feature_note.domain.util

sealed class Resource<out R>(data: R) {
//	data class Failure<out R>(data) : Resource<R>(data)
}