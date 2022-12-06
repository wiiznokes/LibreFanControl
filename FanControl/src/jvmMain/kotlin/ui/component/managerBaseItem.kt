package ui.component

import Source
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.utils.Resources


@Composable
fun baseItem(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    source: Source,
    label: String,
    onNameChange: (String) -> Boolean,
    editModeActivated: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Surface(
                modifier = Modifier
                    .padding(10.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            ) {

                val density = LocalDensity.current

                val finalWidth: MutableState<Dp> = remember { mutableStateOf(0.dp) }



                Column(
                    modifier = Modifier
                        .padding(20.dp)

                ) {
                    Row(
                        modifier = Modifier
                            .onGloballyPositioned {
                                finalWidth.value = density.run { it.size.width.toDp() }
                            }
                    ) {
                        Icon(
                            painter = iconPainter,
                            contentDescription = iconContentDescription
                        )
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                        )
                        when (source) {
                            Source.ADD -> {
                                managerText(
                                    value = name
                                )
                            }

                            Source.BODY -> {
                                val text = remember { mutableStateOf(name) }
                                managerOutlinedTextField(
                                    text = text,
                                    onValueChange = {
                                        onNameChange(it)
                                    },
                                    label = label,
                                    trueName = name
                                )
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )

                    Column(
                        modifier = Modifier
                            .width(finalWidth.value)
                    ) {
                        content()
                    }
                }
            }

        }
        when (source) {
            Source.BODY -> {
                if (editModeActivated) {
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        onClick = {
                            onEditClick()
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("cancel"),
                            contentDescription = Resources.getString("editContentDescriptionRemove"),
                            tint = Color.Red
                        )
                    }
                }
            }

            Source.ADD -> {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    onClick = {
                        onEditClick()
                    }
                ) {
                    Icon(
                        painter = Resources.getIcon("add_circle"),
                        contentDescription = Resources.getString("editContentDescriptionAdd"),
                        tint = Color.Green
                    )
                }
            }
        }
    }
}





