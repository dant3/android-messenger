# Project Structure

## Application Modules

Top-level application entry points:

- `androidApp` — Android application
- `desktopApp` — Desktop (JVM) application
- `iosApp` — iOS application

## Library Modules

All library modules live under `modules/`:

```
modules/
├── core/          — Core platform-agnostic libraries
│   ├── arch/      — Architecture foundations (lifecycle management, Koin DI utilities)
│   ├── database/  — Database infrastructure (SQLDelight setup, platform drivers)
│   ├── uikit/     — Design system (theme, colors, typography, shared components)
│   └── utils/     — Common utilities (logging)
├── feature/       — Feature modules (business logic + UI)
│   └── counter/
│       ├── core/  — Counter business logic (state, store, Koin module)
│       └── ui/    — Counter Compose screen
└── ui/            — App-level UI (composes feature screens into the application UI)
```

### Module Categories

- **core/** — Platform-agnostic business logic and infrastructure (DI, networking, persistence, design system, etc.)
- **feature/** — Feature modules, each split into `core` (logic) and `ui` (Compose screen)
- **ui/** — App-level Compose UI that assembles feature screens

## KMP Source Set Layout

```
<module>/
├── src/
│   ├── commonMain/kotlin/    — Shared multiplatform code
│   ├── commonTest/kotlin/    — Shared tests
│   ├── androidMain/kotlin/   — Android-specific code
│   ├── jvmMain/kotlin/       — Desktop (JVM) specific code
│   └── iosMain/kotlin/       — iOS-specific code
└── build.gradle.kts
```

## Adding a New Module

1. Create `<path>/build.gradle.kts` with the appropriate convention plugin (see CLAUDE.md for the plugin table)
2. Add `include(":<gradle-path>")` to `settings.gradle.kts`

Example for a new core module at `modules/core/network`:

```kotlin
// modules/core/network/build.gradle.kts
plugins {
    id("amber.messenger.compose")
}

dependencies {
    commonMainImplementation(libs.ktor.client.core)
}
```

```kotlin
// settings.gradle.kts — add:
include(":modules:core:network")
```
