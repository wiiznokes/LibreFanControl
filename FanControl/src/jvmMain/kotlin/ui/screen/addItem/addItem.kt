package ui.screen.addItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.itemsList.behaviorList.behaviorAddItemList
import ui.screen.itemsList.controlList.controlAddItem
import ui.screen.itemsList.controlList.controlList
import ui.screen.itemsList.sensor.addItem.sensorAddItemList
import ui.utils.Resources


@Composable
fun addItem(
    currentChoiceType: MutableState<ChoiceType>
) {
    val choiceStates = ChoiceStates()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseSurface)
    ) {
        addItemChoice(currentChoiceType, choiceStates)

        Divider(
            modifier = Modifier.padding(bottom = 10.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )

        LazyColumn {
            when (currentChoiceType.value) {
                ChoiceType.CONTROL -> controlList({ !it.visible }) { index, control ->
                    controlAddItem(
                        control = control,
                        index = index
                    )
                }

                ChoiceType.BEHAVIOR -> behaviorAddItemList()
                ChoiceType.SENSOR -> sensorAddItemList()
            }
        }
    }
}


@Composable
private fun addItemChoice(currentChoiceType: MutableState<ChoiceType>, choiceStates: ChoiceStates) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                currentChoiceType.value = choiceStates.getState(currentChoiceType.value).previous
            }
        ) {
            Icon(
                painter = Resources.getIcon("line_start_arrow_notch"),
                contentDescription = Resources.getString("ct/previous"),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }

        managerText(
            modifier = Modifier,
            text = choiceStates.getState(currentChoiceType.value).title,
            color = MaterialTheme.colorScheme.onSecondary
        )

        IconButton(
            onClick = {
                currentChoiceType.value = choiceStates.getState(currentChoiceType.value).next
            }
        ) {
            Icon(
                painter = Resources.getIcon("line_end_arrow_notch"),
                contentDescription = Resources.getString("ct/next"),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}