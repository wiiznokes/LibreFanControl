package ui.screen.addItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import ui.screen.addItem.behavior.managerAddBehavior
import ui.screen.addItem.control.managerAddControl
import ui.screen.addItem.sensor.managerAddSensor
import ui.utils.Resources


@Composable
fun addItem(
    modifier: Modifier,
    currentChoiceType: MutableState<ChoiceType>
) {
    val choiceStates = ChoiceStates()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        addItemChoice(currentChoiceType, choiceStates)

        Divider(
            modifier = Modifier.padding(bottom = 10.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        when (currentChoiceType.value) {
            ChoiceType.CONTROL -> managerAddControl()
            ChoiceType.BEHAVIOR -> managerAddBehavior()
            ChoiceType.SENSOR -> managerAddSensor()
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
                contentDescription = Resources.getString("ct/previous"),
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
                contentDescription = Resources.getString("ct/next"),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}