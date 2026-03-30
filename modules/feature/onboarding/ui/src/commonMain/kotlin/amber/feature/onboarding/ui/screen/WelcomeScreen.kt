package amber.feature.onboarding.ui.screen

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.app_name
import amber.modules.core.uikit.generated.resources.login
import amber.modules.core.uikit.generated.resources.onboarding_welcome_subtitle
import amber.modules.core.uikit.generated.resources.register
import amber.ui.uikit.preview.PreviewComponent
import amber.ui.uikit.preview.UiPreview
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WelcomeScreen(
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WelcomeContent(
        onRegister = onRegister,
        onLogin = onLogin,
        modifier = modifier,
    )
}

@Composable
private fun WelcomeContent(
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier.padding(horizontal = 32.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Chat,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(96.dp),
        )

        Text(
            text = stringResource(Res.string.app_name),
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = stringResource(Res.string.onboarding_welcome_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRegister,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(Res.string.register))
        }

        OutlinedButton(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(Res.string.login))
        }
    }
}

@UiPreview
@Composable
private fun WelcomeScreenPreview() = PreviewComponent {
    WelcomeContent(
        onRegister = {},
        onLogin = {},
        modifier = Modifier.fillMaxSize(),
    )
}
