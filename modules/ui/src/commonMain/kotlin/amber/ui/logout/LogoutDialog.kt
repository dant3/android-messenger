package amber.ui.logout

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.logout_dialog_confirm
import amber.modules.core.uikit.generated.resources.logout_dialog_dismiss
import amber.modules.core.uikit.generated.resources.logout_dialog_message
import amber.modules.core.uikit.generated.resources.logout_dialog_title
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource

@Composable
fun LogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(Res.string.logout_dialog_title)) },
        text = { Text(text = stringResource(Res.string.logout_dialog_message)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(Res.string.logout_dialog_confirm),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(Res.string.logout_dialog_dismiss))
            }
        },
    )
}
