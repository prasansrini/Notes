package com.my.notes.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider

object ContextTestUtil {
	val TEST_CONTEXT: Context by lazy {
		ApplicationProvider.getApplicationContext()
	}
}