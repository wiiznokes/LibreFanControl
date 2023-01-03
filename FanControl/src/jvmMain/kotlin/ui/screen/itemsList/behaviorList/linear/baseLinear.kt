package ui.screen.itemsList.behaviorList.linear

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import model.item.behavior.Linear
import ui.component.managerText
import ui.utils.Resources


val linearValues = listOf(
    "20", "70", "50", "100"
)

fun linearValues(linear: Linear) = listOf(
    linear.minTemp,
    linear.maxTemp,
    linear.minFanSpeed,
    linear.maxFanSpeed
)

val linearPrefixes = listOf(
    Resources.getString("linear/min_temp"),
    Resources.getString("linear/max_temp"),
    Resources.getString("linear/min_fan_speed"),
    Resources.getString("linear/max_fan_speed")
)
val linearSuffixes = listOf(
    Resources.getString("unity/degree"),
    Resources.getString("unity/degree"),
    Resources.getString("unity/percent"),
    Resources.getString("unity/percent")
)
val linearTypes = listOf(
    LinearParams.MIN_TEMP,
    LinearParams.MAX_TEMP,
    LinearParams.MIN_FAN_SPEED,
    LinearParams.MAX_FAN_SPEED
)


@Composable
fun baseLinear(
    value: Int,
    color: Color,
    enabled: Boolean = true,
    expanded: MutableState<Boolean> = mutableStateOf(true)
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        managerText(
            text = "$value ${Resources.getString("unity/percent")}",
            color = color
        )

        if (enabled) {
            IconButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                val painter = when (expanded.value) {
                    true -> Resources.getIcon("expand_less")
                    false -> Resources.getIcon("expand_more")
                }
                Icon(
                    painter = painter,
                    contentDescription = Resources.getString("ct/choose"),
                    tint = color
                )
            }
        } else {
            Icon(
                painter = Resources.getIcon("expand_less"),
                contentDescription = Resources.getString("ct/choose"),
                tint = color
            )
        }
    }
}