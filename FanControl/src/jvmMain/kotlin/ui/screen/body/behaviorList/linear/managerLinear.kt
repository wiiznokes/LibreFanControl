package ui.screen.body.behaviorList.linear

import State
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.item.behavior.BehaviorItem
import model.item.behavior.LinearBehavior
import ui.component.baseItemBody
import ui.component.managerListChoice
import ui.component.managerNumberTextField
import ui.component.managerText
import ui.utils.Resources

private val viewModel: LinearBehaviorViewModel = LinearBehaviorViewModel()

@Composable
fun linearBehavior(
    behavior: BehaviorItem,
    index: Int,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit
) {
    baseItemBody(
        iconPainter = Resources.getIcon("linear"),
        iconContentDescription = Resources.getString("ct/linear"),
        onNameChange = onNameChange,
        onEditClick = onEditClick,
        item = behavior
    ) {

        val linearBehavior = behavior.extension as LinearBehavior

        managerListChoice(
            text = if (linearBehavior.tempSensorId != null) {
                viewModel.tempList.find {
                    it.id == linearBehavior.tempSensorId
                }!!.libName
            } else null,
            onItemClick = {
                viewModel.setTemp(
                    index = index,
                    tempSensorId = it
                )
            },
            ids = viewModel.tempList.map { it.id },
            names = viewModel.tempList.map { it.libName }
        )

        val values = listOf(
            linearBehavior.minTemp,
            linearBehavior.maxTemp,
            linearBehavior.minFanSpeed,
            linearBehavior.maxFanSpeed
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

        for (i in 0..3) {
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
                id = behavior.itemId,
                type = types[i],
                opposedValue =
                when (i) {
                    0 -> values[1]
                    1 -> values[0]
                    2 -> values[3]
                    3 -> values[2]
                    else -> {
                        throw Exception("impossible index")
                    }
                }

            )
        }
    }
}


@Composable
private fun setting(
    value: Int,
    opposedValue: Int,
    type: LinearParams,
    prefix: String,
    suffix: String,

    onValueChange: (Int) -> String,
    increase: () -> String,
    decrease: () -> String,
    id: Long
) {
    // id use to update value witch use remember
    val configId = State.settings.collectAsState().value.configId

    // if id had change, remember have to update
    // this avoid bug when name of an item
    // get reuse with another item
    val text: MutableState<String> = remember(
        id, configId
    ) { mutableStateOf(value.toString()) }

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
                    text = prefix
                )
                managerNumberTextField(
                    text = text,
                    opposedValue = opposedValue,
                    onValueChange = onValueChange,
                    type = type
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