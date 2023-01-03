package ui.screen.itemsList.behaviorList.flat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources


@Composable
fun baseFlat(
    value: Int,
    enabled: Boolean,
    onLess: (() -> Unit)? = null,
    onMore: (() -> Unit)? = null,
    onValueChange: ((Float) -> Unit)? = null,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        managerText(
            text = Resources.getString("fan_speed"),
            color = color
        )

        Box {
            Row {
                IconButton(
                    onClick = onLess ?: {}
                ) {
                    Icon(
                        painter = Resources.getIcon("remove"),
                        contentDescription = Resources.getString("ct/decrease"),
                        tint = color
                    )
                }
                IconButton(
                    onClick = onMore ?: {}
                ) {
                    Icon(
                        painter = Resources.getIcon("add"),
                        contentDescription = Resources.getString("ct/increase"),
                        tint = color
                    )
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Slider(
            value = value.toFloat(),
            steps = 100,
            valueRange = 0f..100f,
            enabled = enabled,
            onValueChange = onValueChange ?: {},
            modifier = Modifier
                .fillMaxWidth(0.7f)
        )
        Spacer(Modifier.width(10.dp))
        managerText(
            text = "$value ${Resources.getString("unity/percent")}",
            color = color
        )
    }
}