package amber.feature.onboarding.ui.screen

import amber.feature.onboarding.ui.OnboardingUiState
import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.onboarding_forgot_password_button
import amber.modules.core.uikit.generated.resources.onboarding_forgot_password_description
import amber.modules.core.uikit.generated.resources.onboarding_forgot_password_title
import amber.modules.core.uikit.generated.resources.onboarding_login_email_label
import amber.ui.uikit.preview.PreviewComponent
import amber.ui.uikit.preview.UiPreview
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ForgotPasswordScreen(
    state: OnboardingUiState,
    onEmailChange: (String) -> Unit,
    onSendResetCode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ForgotPasswordContent(
        state = state,
        onEmailChange = onEmailChange,
        onSendResetCode = onSendResetCode,
        modifier = modifier,
    )
}

@Composable
private fun ForgotPasswordContent(
    state: OnboardingUiState,
    onEmailChange: (String) -> Unit,
    onSendResetCode: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier.padding(horizontal = 32.dp),
    ) {
        Text(
            text = stringResource(Res.string.onboarding_forgot_password_title),
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = stringResource(Res.string.onboarding_forgot_password_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(Res.string.onboarding_login_email_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = onSendResetCode,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(Res.string.onboarding_forgot_password_button))
            }
        }
    }
}

@UiPreview
@Composable
private fun ForgotPasswordScreenPreview() = PreviewComponent {
    ForgotPasswordContent(
        state = OnboardingUiState(email = "user@example.com"),
        onEmailChange = {},
        onSendResetCode = {},
        modifier = Modifier.fillMaxSize(),
    )
}
