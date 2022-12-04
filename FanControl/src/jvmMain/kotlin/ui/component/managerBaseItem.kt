package ui.component

import Source
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import ui.utils.Resources

@Composable
fun baseItem(
    imageVector: ImageVector,
    iconContentDescription: String,
    name: String,
    label: String? = null,
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
                        imageVector = imageVector,
                        contentDescription = iconContentDescription,
                        tint = Color.Green
                    )
                    when (source) {
                        Source.ADD -> {
                            managerTextField(
                                value = name
                            )
                        }

                        Source.BODY -> {
                            managerOutlinedTextField(
                                value = name,
                                onValueChange = {
                                    onNameChange?.invoke(it)
                                },
                                label = label,
                            )
                        }
                    }
                }

                content()
            }

            when (source) {
                Source.BODY -> {
                    if (editModeActivated?.value == true) {
                        IconButton(
                            onClick = {
                                onEditClick()
                            }
                        ) {
                            Icon(
                                imageVector = Resources.getIcon("add"),
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
                            imageVector = Resources.getIcon("add"),
                            contentDescription = Resources.getString("editContentDescriptionAdd")
                        )
                    }
                }
            }
        }
    }
}





