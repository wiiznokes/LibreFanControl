package ui.screen.addItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.baseSensorAddItem
import ui.component.managerText
import ui.utils.Resources


private val viewModel = AddItemViewModel()

@Composable
fun addItem(
    modifier: Modifier
) {
    val currentChoiceType: MutableState<ChoiceType> = remember {
        mutableStateOf(ChoiceType.BEHAVIOR)
    }
    val choiceStates = ChoiceStates()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        addItemChoice(currentChoiceType, choiceStates)

        Divider(
            modifier = Modifier.padding(bottom = 10.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        when (currentChoiceType.value) {
            ChoiceType.CONTROL -> {
                baseControlListAddItem(
                    controlItemList = viewModel.controlItemList.value,
                    controlList = viewModel.controlList.value,
                    addControl = {
                        viewModel.addControl(it)
                    }
                )
            }

            ChoiceType.BEHAVIOR -> {

            }

            ChoiceType.SENSOR -> {
                // fan
                baseSensorAddItem(
                    iconPainter = Resources.getIcon("toys_fan"),
                    iconContentDescription = Resources.getString("fan_icon_content_description"),
                    name = Resources.getString("add_item_default_fan_name"),
                    onEditClick = {
                        viewModel.addFan()
                    },
                    sensorName = Resources.getString("add_item_default_fan_name"),
                    sensorValue = "1000 ${Resources.getString("rpm")}"
                )

                // temp
                baseSensorAddItem(
                    iconPainter = Resources.getIcon("thermometer"),
                    iconContentDescription = Resources.getString("temp_icon_content_description"),
                    name = Resources.getString("add_item_default_temp_name"),
                    onEditClick = {
                        viewModel.addTemp()
                    },
                    sensorName = Resources.getString("add_item_default_temp_name"),
                    sensorValue = "50 ${Resources.getString("degree")}"
                )
            }
        }


    }
}


@Composable
fun addItemChoice(currentChoiceType: MutableState<ChoiceType>, choiceStates: ChoiceStates) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                currentChoiceType.value = choiceStates.getState(currentChoiceType.value)
                    .previous
            }
        ) {
            Icon(
                painter = Resources.getIcon("arrow_back"),
                contentDescription = Resources.getString("add_item_previous_button_content_description"),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        managerText(
            modifier = Modifier,
            text = choiceStates.getState(currentChoiceType.value)
                .title
        )

        IconButton(
            onClick = {
                currentChoiceType.value = choiceStates.getState(currentChoiceType.value)
                    .next
            }
        ) {
            Icon(
                painter = Resources.getIcon("arrow_forward"),
                contentDescription = Resources.getString("add_item_next_button_content_description"),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}