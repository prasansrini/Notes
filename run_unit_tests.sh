#!/usr/bin/env bash

./gradlew app:installDebugAndroidTest
adb shell am instrument -w -r -e package com.my.notes -e debug true com.my.notes.test/androidx.test.runner.AndroidJUnitRunner