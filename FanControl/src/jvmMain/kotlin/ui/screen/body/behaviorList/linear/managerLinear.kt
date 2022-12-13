package ui.screen.body.behaviorList.linear

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        setting(
            value = behavior.linearBehavior!!.minTemp,
            prefix = Resources.getString("linear/min_temp"),
            suffix = Resources.getString("unity/degree"),
            onValueChange = {
                viewModel.onChange(
                    index = index,
                    text = it,
                    type = LinearParams.MIN_TEMP
                )
            },
            increase = {
                viewModel.increase(
                    index = index,
                    value = behavior.linearBehavior.minTemp,
                    type = LinearParams.MIN_TEMP
                )
            },
            decrease = {
                viewModel.decrease(
                    index = index,
                    value = behavior.linearBehavior.minTemp,
                    type = LinearParams.MIN_TEMP
                )
            },
        )
        setting(
            value = behavior.linearBehavior.maxTemp,
            prefix = Resources.getString("linear/max_temp"),
            suffix = Resources.getString("unity/degree"),
            onValueChange = {
                viewModel.onChange(
                    index = index,
                    text = it,
                    type = LinearParams.MAX_TEMP
                )
            },
            increase = {
                viewModel.increase(
                    index = index,
                    value = behavior.linearBehavior.maxTemp,
                    type = LinearParams.MAX_TEMP
                )
            },
            decrease = {
                viewModel.decrease(
                    index = index,
                    value = behavior.linearBehavior.maxTemp,
                    type = LinearParams.MAX_TEMP
                )
            },
        )
        setting(
            value = behavior.linearBehavior.minFanSpeed,
            prefix = Resources.getString("linear/min_fan_speed"),
            suffix = Resources.getString("unity/rpm"),
            onValueChange = {
                viewModel.onChange(
                    index = index,
                    text = it,
                    type = LinearParams.MIN_FAN_SPEED
                )
            },
            increase = {
                viewModel.increase(
                    index = index,
                    value = behavior.linearBehavior.minFanSpeed,
                    type = LinearParams.MIN_FAN_SPEED
                )
            },
            decrease = {
                viewModel.decrease(
                    index = index,
                    value = behavior.linearBehavior.minFanSpeed,
                    type = LinearParams.MIN_FAN_SPEED
                )
            },
        )
        setting(
            value = behavior.linearBehavior.maxFanSpeed,
            prefix = Resources.getString("linear/max_fan_speed"),
            suffix = Resources.getString("unity/rpm"),
            onValueChange = {
                viewModel.onChange(
                    index = index,
                    text = it,
                    type = LinearParams.MAX_FAN_SPEED
                )
            },
            increase = {
                viewModel.increase(
                    index = index,
                    value = behavior.linearBehavior.maxFanSpeed,
                    type = LinearParams.MAX_FAN_SPEED
                )
            },
            decrease = {
                viewModel.decrease(
                    index = index,
                    value = behavior.linearBehavior.maxFanSpeed,
                    type = LinearParams.MAX_FAN_SPEED
                )
            },
        )
    }
}


@Composable
private fun setting(
    value: Int,
    prefix: String,
    suffix: String,

    onValueChange: (String) -> Unit,
    increase: () -> Unit,
    decrease: () -> Unit,
) {
    val text: MutableState<String> = mutableStateOf(value.toString())

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        managerText(
            text = prefix
        )
        managerTextField(
            text = text,
            onValueChange = onValueChange
        )
        managerText(
            text = suffix
        )
        Column {
            IconButton(
                onClick = {
                    increase()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("ct/increase")
                )
            }
            IconButton(
                onClick = {
                    decrease()
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