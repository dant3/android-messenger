package amber.ui.uikit

import amber.ui.uikit.theme.AmberMaterialTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    AmberMaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
            modifier = modifier
        ) {
            var counter by remember { mutableStateOf(0) }

            Text(
                text = "Hello, world!",
                style = MaterialTheme.typography.titleLarge
            )

            Text("Your counter: $counter")

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { counter++ }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                }
                Button(onClick = { counter-- }) {
                    Icon(Icons.Filled.Remove, contentDescription = null)
                }
            }
        }
    }
}