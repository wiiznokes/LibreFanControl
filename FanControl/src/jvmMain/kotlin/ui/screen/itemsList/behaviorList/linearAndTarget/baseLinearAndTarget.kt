package ui.screen.itemsList.behaviorList.linearAndTarget

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
import ui.component.managerText
import ui.screen.itemsList.behaviorList.linearAndTarget.linear.LinearParams
import ui.screen.itemsList.behaviorList.linearAndTarget.target.TargetParams
import ui.utils.Resources


val linAndTarSuffixes = listOf(
    Resources.getString("unity/degree"),
    Resources.getString("unity/degree"),
    Resources.getString("unity/percent"),
    Resources.getString("unity/percent")
)

interface LinAndTarParams

fun isError(params: LinAndTarParams, str: String, opposedValue: Int): Boolean {
    val value = try {
        str.toInt()
    } catch (e: NumberFormatException) {
        return true
    }

    when (params) {
        is LinearParams -> {
            return try {
                when (params) {
                    LinearParams.MIN_TEMP -> value >= opposedValue
                    LinearParams.MAX_TEMP -> value <= opposedValue
                    LinearParams.MIN_FAN_SPEED -> value >= opposedValue
                    LinearParams.MAX_FAN_SPEED -> value <= opposedValue
                }
            } catch (e: NumberFormatException) {
                true
            }
        }

        is TargetParams -> {
            return try {
                when (params) {
                    TargetParams.IDLE_TEMP -> value >= opposedValue
                    TargetParams.LOAD_TEMP -> value <= opposedValue
                    TargetParams.IDLE_FAN_SPEED -> value >= opposedValue
                    TargetParams.LOAD_FAN_SPEED -> value <= opposedValue
                }
            } catch (e: NumberFormatException) {
                true
            }
        }

        else -> throw IllegalArgumentException()
    }
}

/**
 * base linear and target behavior
 */
@Composable
fun baseLinAndTar(
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