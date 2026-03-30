package amber.feature.onboarding.ui.screen

import amber.feature.onboarding.ui.OnboardingUiState
import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.onboarding_reset_password_button
import amber.modules.core.uikit.generated.resources.onboarding_reset_password_code_label
import amber.modules.core.uikit.generated.resources.onboarding_reset_password_new_password_label
import amber.modules.core.uikit.generated.resources.onboarding_reset_password_title
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ResetPasswordScreen(
    email: String,
    state: OnboardingUiState,
    onCodeChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onResetPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ResetPasswordContent(
        state = state,
        onCodeChange = onCodeChange,
        onNewPasswordChange = onNewPasswordChange,
        onResetPassword = onResetPassword,
        modifier = modifier,
    )
}

@Composable
private fun ResetPasswordContent(
    state: OnboardingUiState,
    onCodeChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onResetPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier.padding(horizontal = 32.dp),
    ) {
        Text(
            text = stringResource(Res.string.onboarding_reset_password_title),
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.confirmationCode,
            onValueChange = onCodeChange,
            label = { Text(stringResource(Res.string.onboarding_reset_password_code_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = state.newPassword,
            onValueChange = onNewPasswordChange,
            label = { Text(stringResource(Res.string.onboarding_reset_password_new_password_label)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
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
                onClick = onResetPassword,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(Res.string.onboarding_reset_password_button))
            }
        }
    }
}

@UiPreview
@Composable
private fun ResetPasswordScreenPreview() = PreviewComponent {
    ResetPasswordContent(
        state = OnboardingUiState(confirmationCode = "123456", newPassword = "newpass"),
        onCodeChange = {},
        onNewPasswordChange = {},
        onResetPassword = {},
        modifier = Modifier.fillMaxSize(),
    )
}
