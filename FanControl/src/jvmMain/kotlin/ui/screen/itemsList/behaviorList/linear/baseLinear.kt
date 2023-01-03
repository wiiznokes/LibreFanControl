package ui.screen.itemsList.behaviorList.linear

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.item.behavior.LinearBehavior
import ui.component.managerText
import ui.utils.Resources


val linearValues = listOf(
    "20", "70", "50", "100"
)

fun linearValues(linearBehavior: LinearBehavior) = listOf(
    linearBehavior.minTemp,
    linearBehavior.maxTemp,
    linearBehavior.minFanSpeed,
    linearBehavior.maxFanSpeed
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
    text: @Composable () -> Unit,
    prefix: String,
    suffix: String,

    increase: (() -> Unit)? = null,
    decrease: (() -> Unit)? = null,
    color: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                managerText(
                    modifier = Modifier
                        .width(110.dp),
                    text = prefix,
                    color = color
                )
                text()
                Spacer(Modifier.width(5.dp))
                managerText(
                    text = suffix,
                    color = color
                )
            }
        }
        Column {
            IconButton(
                onClick = increase ?: {}
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("ct/increase"),
                    tint = color
                )
            }
            IconButton(
                onClick = decrease ?: {}
            ) {
                Icon(
                    painter = Resources.getIcon("remove"),
                    contentDescription = Resources.getString("ct/decrease"),
                    tint = color
                )
            }
        }
    }
}