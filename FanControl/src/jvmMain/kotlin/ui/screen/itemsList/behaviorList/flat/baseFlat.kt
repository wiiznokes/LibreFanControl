package ui.screen.itemsList.behaviorList.flat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        color = LocalColors.current.inputVariant,
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
                color = LocalColors.current.onInputVariant,
                modifier = Modifier.padding(start = LocalSpaces.current.small)
            )

            Box {
                Row {
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = onLess),
                        painter = Resources.getIcon("sign/minus/remove24"),
                        contentDescription = null,
                        tint = LocalColors.current.onInputVariant
                    )

                    Icon(
                        modifier = Modifier
                            .clickable(onClick = onMore),
                        painter = Resources.getIcon("sign/plus/add24"),
                        contentDescription = null,
                        tint = LocalColors.current.onInputVariant
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
            modifier = Modifier
                .fillMaxWidth(0.65f),
            value = value.toFloat(),
            steps = 100,
            valueRange = 0f..100f,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = LocalColors.current.onInactiveInput,
                activeTickColor = LocalColors.current.input,
                inactiveTickColor = LocalColors.current.inactiveInput
            )
        )

        managerText(
            text = "$value ${Resources.getString("unity/percent")}",
            color = LocalColors.current.onMainContainer
        )
    }
}