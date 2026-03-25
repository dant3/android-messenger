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
│   └── arch/      — Architecture foundations (Koin DI utilities, base classes)
├── ui/            — UI-related libraries
│   └── uikit/     — Design system (theme, colors, typography, shared components)
```

### Module Categories

- **core/** — Platform-agnostic business logic and infrastructure (DI, networking, persistence, etc.)
- **ui/** — UI components and feature screens

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
