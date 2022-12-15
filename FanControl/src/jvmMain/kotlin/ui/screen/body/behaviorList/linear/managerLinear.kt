package ui.screen.body.behaviorList.linear

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.item.behavior.BehaviorItem
import ui.component.baseItemBody
import ui.component.managerText
import ui.component.managerTextField
import ui.utils.Resources

private val viewModel: LinearBehaviorViewModel = LinearBehaviorViewModel()

@Composable
fun linearBehavior(
    behavior: BehaviorItem,
    index: Int,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,
) {
    baseItemBody(
        iconPainter = Resources.getIcon("linear"),
        iconContentDescription = Resources.getString("ct/linear"),
        name = behavior.name,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick
    ) {


        val values = listOf(
            behavior.linearBehavior!!.minTemp,
            behavior.linearBehavior.maxTemp,
            behavior.linearBehavior.minFanSpeed,
            behavior.linearBehavior.maxFanSpeed
        )

        val prefixes = listOf(
            Resources.getString("linear/min_temp"),
            Resources.getString("linear/max_temp"),
            Resources.getString("linear/min_fan_speed"),
            Resources.getString("linear/max_fan_speed")
        )
        val suffixes = listOf(
            Resources.getString("unity/degree"),
            Resources.getString("unity/degree"),
            Resources.getString("unity/percent"),
            Resources.getString("unity/percent")
        )
        val types = listOf(
            LinearParams.MIN_TEMP,
            LinearParams.MAX_TEMP,
            LinearParams.MIN_FAN_SPEED,
            LinearParams.MAX_FAN_SPEED
        )

        for(i in 0 .. 3) {
            setting(
                value = values[i],
                prefix = prefixes[i],
                suffix = suffixes[i],
                onValueChange = {
                    viewModel.onChange(
                        index = index,
                        value = it,
                        type = types[i]
                    )
                },
                increase = {
                    viewModel.increase(
                        index = index,
                        type = types[i]
                    )
                },
                decrease = {
                    viewModel.decrease(
                        index = index,
                        type = types[i]
                    )
                },
            )
        }
    }
}


@Composable
private fun setting(
    value: Int,
    prefix: String,
    suffix: String,

    onValueChange: (Int) -> String,
    increase: () -> String,
    decrease: () -> String,
) {

    val text: MutableState<String> = remember { mutableStateOf(value.toString()) }
    val isError = remember { mutableStateOf(false) }

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
                        .width(120.dp),
                    text = prefix
                )
                managerTextField(
                    text = text,
                    onValueChange = onValueChange,
                    isError = isError
                )
                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                )
                managerText(
                    text = suffix
                )
            }
        }
        Column {
            IconButton(
                onClick = {
                    val finalValue = increase()
                    text.value = finalValue
                    isError.value = false
                }
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("ct/increase")
                )
            }
            IconButton(
                onClick = {
                    val finalValue = decrease()
                    text.value = finalValue
                    isError.value = false
                }
            ) {
                Icon(
                    painter = Resources.getIcon("remove"),
                    contentDescription = Resources.getString("ct/decrease")
                )
            }
        }
    }
}