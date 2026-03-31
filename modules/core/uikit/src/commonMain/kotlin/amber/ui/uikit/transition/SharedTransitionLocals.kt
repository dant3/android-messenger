package amber.ui.uikit.transition

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier

val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
val LocalAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun Modifier.optionalSharedElement(key: String): Modifier {
    val sharedTransitionScope = LocalSharedTransitionScope.current ?: return this
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current ?: return this
    return with(sharedTransitionScope) {
        this@optionalSharedElement.sharedElement(
            rememberSharedContentState(key),
            animatedVisibilityScope,
        )
    }
}
