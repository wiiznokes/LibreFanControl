package ui.screen.itemsList.behaviorList.flat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources


@Composable
fun baseFlat(
    value: Int,
    onLess: () -> Unit,
    onMore: () -> Unit,
    onValueChange: (Float) -> Unit
) {
    Surface(
        color = LocalColors.current.mainSurface,
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            managerText(
                text = Resources.getString("fan_speed"),
                color = LocalColors.current.onMainSurface,
                modifier = Modifier.padding(start = LocalSpaces.current.small)
            )

            Box {
                Row {
                    Icon(
                        modifier = Modifier
                            .clickable (onClick = onLess),
                        painter = Resources.getIcon("sign/minus/remove24"),
                        contentDescription = null,
                        tint = LocalColors.current.onMainSurface
                    )

                    Icon(
                        modifier = Modifier
                            .clickable (onClick = onMore),
                        painter = Resources.getIcon("sign/plus/add24"),
                        contentDescription = null,
                        tint = LocalColors.current.onMainSurface
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
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(0.65f),
            colors = SliderDefaults.colors(
                thumbColor = LocalColors.current.onInput,
                activeTickColor = LocalColors.current.mainSurface,
                inactiveTickColor = LocalColors.current.input,

                activeTrackColor = Color.Green
            )
        )
        Spacer(Modifier.width(LocalSpaces.current.small))
        managerText(
            text = "$value ${Resources.getString("unity/percent")}",
            color = LocalColors.current.onMainContainer
        )
    }
}