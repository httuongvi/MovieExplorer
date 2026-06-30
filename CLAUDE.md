# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

MovieExplorer is an Android app (package `com.tuongvi.movieexplorer`) built with Kotlin and Jetpack Compose. It is in an early/learning stage: the Compose UI scaffolding exists in `MainActivity` but is currently commented out, and `onCreate` instead runs Kotlin exercise functions from `Playground.kt` (`grade`, `safeLength`, `fizzbuzz`). Output is observed via `Log.d` in Logcat rather than on-screen.

## Build & Test

Use the Gradle wrapper (`./gradlew`):

- Build debug APK: `./gradlew assembleDebug`
- Full build incl. lint & tests: `./gradlew build`
- Android Lint: `./gradlew lint`
- Unit tests (JVM, `app/src/test/`): `./gradlew test` — run a single test class: `./gradlew testDebugUnitTest --tests "com.tuongvi.movieexplorer.ExampleUnitTest"`
- Instrumented tests (needs emulator/device, `app/src/androidTest/`): `./gradlew connectedAndroidTest`
- Install on a connected device: `./gradlew installDebug`

## Configuration

- `compileSdk`/`targetSdk` = 36, `minSdk` = 24; Java/Kotlin target = 11.
- Dependency versions are centralized in the version catalog at [gradle/libs.versions.toml](gradle/libs.versions.toml) — add or bump dependencies there (`libs.*` / `libs.plugins.*`) rather than hardcoding versions in `build.gradle.kts`.
- Compose is enabled via `buildFeatures { compose = true }`; the Compose BOM pins all `androidx.compose.*` versions.

## Structure notes

- Single Gradle module `:app`. Entry point is [app/src/main/java/com/tuongvi/movieexplorer/MainActivity.kt](app/src/main/java/com/tuongvi/movieexplorer/MainActivity.kt).
- Compose theme (color, typography, `MovieExplorerTheme`) lives under `ui/theme/`.
