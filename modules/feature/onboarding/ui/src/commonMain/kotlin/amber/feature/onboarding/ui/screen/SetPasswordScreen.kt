package amber.feature.onboarding.ui.screen

import amber.feature.onboarding.ui.OnboardingUiState
import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.onboarding_set_password_button
import amber.modules.core.uikit.generated.resources.onboarding_set_password_description
import amber.modules.core.uikit.generated.resources.onboarding_set_password_label
import amber.modules.core.uikit.generated.resources.onboarding_set_password_title
import amber.ui.uikit.preview.PreviewComponent
import amber.ui.uikit.preview.UiPreview
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SetPasswordScreen(
    state: OnboardingUiState,
    onPasswordChange: (String) -> Unit,
    onSetPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SetPasswordContent(
        state = state,
        onPasswordChange = onPasswordChange,
        onSetPassword = onSetPassword,
        modifier = modifier,
    )
}

@Composable
private fun SetPasswordContent(
    state: OnboardingUiState,
    onPasswordChange: (String) -> Unit,
    onSetPassword: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier.padding(horizontal = 32.dp),
    ) {
        Text(
            text = stringResource(Res.string.onboarding_set_password_title),
            style = MaterialTheme.typography.headlineMedium,
        )

        Text(
            text = stringResource(Res.string.onboarding_set_password_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(Res.string.onboarding_set_password_label)) },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = null,
                    )
                }
            },
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
                onClick = onSetPassword,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(Res.string.onboarding_set_password_button))
            }
        }
    }
}

@UiPreview
@Composable
private fun SetPasswordScreenPreview() = PreviewComponent {
    SetPasswordContent(
        state = OnboardingUiState(password = "secret"),
        onPasswordChange = {},
        onSetPassword = {},
        modifier = Modifier.fillMaxSize(),
    )
}
