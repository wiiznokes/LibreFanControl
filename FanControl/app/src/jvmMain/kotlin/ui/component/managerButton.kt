package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import ui.theme.LocalColors

@Composable
fun managerButton(
    modifier: Modifier = Modifier
        .width(80.dp),
    onClick: () -> Unit,
    text: String,
    icon: Painter? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = LocalColors.current.inputVariant
    ),

    ) {

    Button(
        onClick = onClick,
        colors = colors
    ) {

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            managerText(
                text = text,
                color = LocalColors.current.onInputVariant
            )

            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = LocalColors.current.onInputVariant
                )
            }
        }
    }
}