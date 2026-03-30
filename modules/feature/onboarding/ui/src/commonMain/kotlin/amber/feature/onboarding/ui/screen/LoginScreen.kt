package amber.feature.onboarding.ui.screen

import amber.feature.onboarding.ui.OnboardingUiState
import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.onboarding_login_button
import amber.modules.core.uikit.generated.resources.onboarding_login_email_label
import amber.modules.core.uikit.generated.resources.onboarding_login_forgot_password
import amber.modules.core.uikit.generated.resources.onboarding_login_password_label
import amber.modules.core.uikit.generated.resources.onboarding_login_title
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LoginScreen(
    state: OnboardingUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LoginContent(
        state = state,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onLogin = onLogin,
        onForgotPassword = onForgotPassword,
        modifier = modifier,
    )
}

@Composable
private fun LoginContent(
    state: OnboardingUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier.padding(horizontal = 32.dp),
    ) {
        Text(
            text = stringResource(Res.string.onboarding_login_title),
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = onEmailChange,
            label = { Text(stringResource(Res.string.onboarding_login_email_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(Res.string.onboarding_login_password_label)) },
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
                onClick = onLogin,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(Res.string.onboarding_login_button))
            }
        }

        TextButton(onClick = onForgotPassword) {
            Text(stringResource(Res.string.onboarding_login_forgot_password))
        }
    }
}

@UiPreview
@Composable
private fun LoginScreenPreview() = PreviewComponent {
    LoginContent(
        state = OnboardingUiState(email = "user@example.com"),
        onEmailChange = {},
        onPasswordChange = {},
        onLogin = {},
        onForgotPassword = {},
        modifier = Modifier.fillMaxSize(),
    )
}
