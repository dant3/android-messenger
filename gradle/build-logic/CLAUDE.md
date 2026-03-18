# Gradle Build Logic — Sharp Edges

## Convention Plugin IDs

| Plugin ID | Use For |
|-----------|---------|
| `amber.messenger.kotlin` | Pure Kotlin JVM libraries |
| `amber.messenger.android.library` | Android libraries (auto-enables test fixtures) |
| `amber.messenger.android.library.compose` | Android libraries with Compose |
| `amber.messenger.android.application` | App module only |

**Don't manually apply** `com.android.library`, `kotlin-android`, or `kotlin-parcelize` — convention plugins handle composition. Each plugin automatically applies Detekt, Kotlin config, and Android config.

## Git-Based Versioning

- **At tagged commit**: exact tag version (e.g., `1.2.3`)
- **Post-tag commits**: `nextMinor-SNAPSHOT+metadata` (e.g., `1.3.0-SNAPSHOT+ANDROID-1537` on branch, `1.3.0-SNAPSHOT+a1b2c3d` on master)

`GitInfo` reads `GIT_BRANCH`/`GIT_COMMIT` env vars first (CI), falls back to JGit.

## Android Configuration

- **compileSdk/targetSdk**: 36 | **minSdk**: 28
- **JVM target**: Java 21 with core library desugaring enabled
- **All warnings are errors**: `allWarningsAsErrors = true`

## Namespace Auto-Generation

Module path → namespace automatically:
- `:modules:core:auth` → `amber.messenger.modules.core.auth`
- `:modules:ui:feature:chat-screen` → `amber.messenger.modules.ui.feature.chat.screen`

Don't set `namespace` manually in module build files.

## Build Features Disabled by Default

`buildConfig`, `aidl`, `renderScript`, `resValues`, `shaders` are all **off**. Enable explicitly in module's `build.gradle.kts` if needed.

## Robolectric SDK Override

Locked to SDK 35 (`robolectric.sdk=35`) because Robolectric 4.16 is incompatible with Paparazzi. Tests run against Android 15 (SDK 35) APIs even though app targets SDK 36.

## Test Fixtures

Automatically enabled for all `amber.messenger.android.library` modules. No per-module opt-in needed.

Detekt config: `gradle/detekt.yml`.

## Compose Stability

`gradle/compose-stability.conf` marks as stable: Java time types, kotlinx immutable collections, and all domain entities (`amber.messenger.domain.entities.**`).
