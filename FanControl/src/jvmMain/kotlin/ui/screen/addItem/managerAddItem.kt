package ui.screen.addItem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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

        Divider(
            modifier = Modifier.padding(bottom = 10.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        Column {


            when (currentChoiceType.value) {
                ChoiceType.CONTROL -> {
                    managerItemAddItem(
                        iconPainter = Resources.getIcon("alternate_email"),
                        iconContentDescription = Resources.getString("control_icon_content_description"),
                        name = Resources.getString("add_item_default_control_name"),
                        onEditClick = {

                        }
                    ) {
                        Row {
                            Switch(
                                checked = true,
                                onCheckedChange = null,
                                enabled = false
                            )
                            managerChoiceAddItem(
                                text = Resources.getString("add_item_default_behavior_name")
                            )
                        }
                        managerText(
                            text = "50 %"
                        )
                    }
                }

                ChoiceType.BEHAVIOR -> {
                    managerItemAddItem(
                        iconPainter = Resources.getIcon("horizontal_rule"),
                        iconContentDescription = Resources.getString("behavior_icon_content_description"),
                        name = Resources.getString("add_item_default_behavior_name"),
                        onEditClick = {

                        }
                    ) {

                    }
                }

                ChoiceType.SENSOR -> {
                    managerItemAddItem(
                        iconPainter = Resources.getIcon("toys_fan"),
                        iconContentDescription = Resources.getString("fan_icon_content_description"),
                        name = Resources.getString("add_item_default_fan_name"),
                        onEditClick = {

                        }
                    ) {
                        managerChoiceAddItem(
                            text = Resources.getString("add_item_default_fan_name")
                        )
                        managerText(
                            text = "1000 ${Resources.getString("rpm")}"
                        )
                    }
                    managerItemAddItem(
                        iconPainter = Resources.getIcon("thermometer"),
                        iconContentDescription = Resources.getString("temp_icon_content_description"),
                        name = Resources.getString("add_item_default_temp_name"),
                        onEditClick = {

                        }
                    ) {
                        managerChoiceAddItem(
                            text = Resources.getString("add_item_default_temp_name")
                        )
                        managerText(
                            text = "50 ${Resources.getString("degree")}"
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun managerChoiceAddItem(
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        managerText(
            text = text
        )
        Icon(
            painter = Resources.getIcon("expand_more"),
            contentDescription = Resources.getString("change_sensor_button_content_description")
        )
    }
}


@Composable
fun managerItemAddItem(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Box {
        Surface(
            modifier = Modifier
                .padding(18.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row {
                    Icon(
                        painter = iconPainter,
                        contentDescription = iconContentDescription
                    )
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                    )

                    managerText(
                        text = name
                    )
                }
                content()
            }
        }
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd),
            onClick = {
                onEditClick()
            }
        ) {
            Icon(
                painter = Resources.getIcon("add_circle"),
                contentDescription = Resources.getString("edit_add_button_content_description"),
                tint = Color.Green
            )
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