package amber.ui.uikit.scaffold

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.back
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigableTopAppBar(
    title: String,
    onNavigateUp: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    Surface(shadowElevation = 3.dp) {
        CenterAlignedTopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                if (onNavigateUp != null) {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back),
                        )
                    }
                }
            },
            actions = actions,
            scrollBehavior = scrollBehavior,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigableTopAppBar(
    title: StringResource,
    onNavigateUp: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    NavigableTopAppBar(
        title = stringResource(title),
        onNavigateUp = onNavigateUp,
        scrollBehavior = scrollBehavior,
        actions = actions,
    )
}
