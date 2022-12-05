package ui.component

import Source
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import ui.utils.Resources


@Composable
fun baseItem(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    source: Source,
    label: String? = null,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: StateFlow<Boolean>? = null,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Box(
        modifier = Modifier
            .background(Color.Green)
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
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


                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .width(IntrinsicSize.Min)
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth(
                                align = Alignment.Start,
                                unbounded = true
                            )
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
                                    label = "name"
                                )
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )

                    content()

                }
            }

        }
        when (source) {
            Source.BODY -> {
                if (editModeActivated?.value == true) {
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





