package ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun managerText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color,
    enabled: Boolean = true,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1,
) {
    Text(
        modifier = modifier,
        text = text,
        color = if (enabled)
            color
        else color.copy(
            alpha = 0.8f
        ),
        maxLines = maxLines,
        style = style,
        overflow = overflow
    )
}