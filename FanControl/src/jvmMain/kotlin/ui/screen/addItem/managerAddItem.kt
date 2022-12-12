package ui.screen.addItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources


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

        Spacer(modifier = Modifier.height(25.dp))

        managerListAddItem {
            when (currentChoiceType.value) {
                ChoiceType.BEHAVIOR -> {

                }
                ChoiceType.SENSOR -> {

                }
                ChoiceType.CONTROL -> {

                }
            }
        }

    }


}

@Composable
fun managerListAddItem(
    content: ()->Unit
) {

    LazyColumn {
        content()
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