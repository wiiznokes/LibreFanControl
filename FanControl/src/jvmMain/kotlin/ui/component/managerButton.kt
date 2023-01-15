package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun managerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    icon: Painter? = null
) {
    val color = MaterialTheme.colorScheme.onTertiary
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.tertiary
        )
    ) {

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            managerText(
                text = text,
                color = color
            )

            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = color
                )
            }
        }
    }
}