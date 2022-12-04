package ui.component

import Source
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import ui.utils.Resources

@Composable
private fun baseItem(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    label: String,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: StateFlow<Boolean>? = null,
    onEditClick: () -> Unit,
    source: Source,
    content: @Composable (ColumnScope.() -> Unit)
) {

    Surface(
        modifier = Modifier
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Box {

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row {
                    Icon(
                        painter = iconPainter,
                        contentDescription = iconContentDescription
                    )
                    when(source) {
                        Source.ADD -> {
                            TextField(
                                modifier = Modifier,
                                value = name,
                                onValueChange = {},
                                textStyle = MaterialTheme.typography.bodyMedium,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.primary,
                                )
                            )
                        }
                        Source.BODY -> {
                            OutlinedTextField(
                                modifier = Modifier,
                                value = name,
                                onValueChange = {
                                    onNameChange?.invoke(it)
                                },
                                textStyle = MaterialTheme.typography.bodyMedium,
                                label = { Text(label) },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = MaterialTheme.colorScheme.onPrimary,
                                    containerColor = MaterialTheme.colorScheme.primary,
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }

                content()
            }

            when(source) {
                Source.BODY -> {
                    if(editModeActivated?.value == true) {
                        IconButton(
                            onClick = {
                                onEditClick()
                            }
                        ) {
                            Icon(
                                painter = Resources.getIcon("add"),
                                contentDescription = Resources.getString("editContentDescriptionRemove")
                            )
                        }
                    }
                }
                Source.ADD -> {
                    IconButton(
                        onClick = {
                            onEditClick()
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("add"),
                            contentDescription = Resources.getString("editContentDescriptionAdd")
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun baseSensor(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    label: String,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: StateFlow<Boolean>? = null,
    onEditClick: () -> Unit,
    source: Source,

    sensorName: String,
    onChangeSensorClick: (() -> Unit)? = null
) {
    baseItem(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        name = name,
        label = label,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        source = source,
    ) {
        Row {
            TextField(
                modifier = Modifier,
                value = sensorName,
                onValueChange = {},
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
            IconButton(
                onClick = {
                    onChangeSensorClick?.invoke()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("changeSensorContentDescription")
                )
            }
        }
    }
}


