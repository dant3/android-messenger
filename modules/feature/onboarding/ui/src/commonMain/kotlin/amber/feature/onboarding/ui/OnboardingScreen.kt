package amber.feature.onboarding.ui

import amber.feature.onboarding.core.OnboardingState
import amber.feature.onboarding.core.OnboardingStore
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
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
fun OnboardingScreen(
    store: OnboardingStore,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by store.state.collectAsState()

    if (state.completed) {
        onComplete()
        return
    }

    OnboardingContent(
        state = state,
        onNext = { store.nextStep() },
        onBack = { store.previousStep() },
        onComplete = { store.complete() },
        modifier = modifier,
    )
}

@Composable
private fun OnboardingContent(
    state: OnboardingState,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp, Alignment.CenterVertically),
        modifier = modifier,
    ) {
        Text(
            text = state.step.title,
            style = MaterialTheme.typography.headlineMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = state.step.description,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (!state.isFirstStep) {
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Back")
                }
            }

            Button(
                onClick = if (state.isLastStep) onComplete else onNext,
                modifier = Modifier.weight(1f),
            ) {
                Text(if (state.isLastStep) "Get Started" else "Next")
            }
        }
    }
}
