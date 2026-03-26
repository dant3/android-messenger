package amber.feature.counter.ui

import amber.feature.counter.core.CounterStore
import amber.ui.uikit.theme.AmberMaterialTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CounterScreen(
    store: CounterStore,
    modifier: Modifier = Modifier,
) {
    val count by store.count.collectAsState(0L)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier,
    ) {
        Text(
            text = "Counter",
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = "$count",
            style = MaterialTheme.typography.displayMedium,
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { store.decrement() }) {
                Icon(Icons.Filled.Remove, contentDescription = "Decrement")
            }
            OutlinedButton(onClick = { store.reset() }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Reset")
            }
            Button(onClick = { store.increment() }) {
                Icon(Icons.Filled.Add, contentDescription = "Increment")
            }
        }
    }
}
