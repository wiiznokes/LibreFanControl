package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.utils.Resources

@Composable
fun managerExpandItem(
    value: Int,
    color: Color,
    enabled: Boolean = true,
    expanded: MutableState<Boolean> = mutableStateOf(true),
    suffix: String = Resources.getString("unity/percent")
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        managerText(
            text = "$value $suffix",
            color = color
        )

        if (enabled) {
            IconButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                val painter = when (expanded.value) {
                    true -> Resources.getIcon("arrow/expand/expand_less")
                    false -> Resources.getIcon("arrow/expand/expand_more")
                }
                Icon(
                    painter = painter,
                    contentDescription = Resources.getString("ct/choose"),
                    tint = color
                )
            }
        } else {
            Icon(
                modifier = Modifier,
                painter = Resources.getIcon("arrow/expand/expand_less"),
                contentDescription = Resources.getString("ct/choose"),
                tint = color
            )
        }
    }
}