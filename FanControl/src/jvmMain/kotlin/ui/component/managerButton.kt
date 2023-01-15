package ui.component

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun managerButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        managerText(
            text = text,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}