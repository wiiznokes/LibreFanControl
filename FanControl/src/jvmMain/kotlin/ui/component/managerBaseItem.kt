package ui.component


import Source
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun baseItemBody(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    val text = mutableStateOf(name)

    baseItem(
        source = Source.BODY,
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        contentEditIcon = {
            Icon(
                painter = Resources.getIcon("cancel"),
                contentDescription = Resources.getString("edit_remove_button_content_description"),
                tint = Color.Red
            )
        },
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        contentName = {
            managerOutlinedTextField(
                text = text,
                onValueChange = {
                    onNameChange(it)
                },
                label = Resources.getString("label_item_name")
            )
        },

        ) {
        content()
    }
}

@Composable
fun baseItemAddItem(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    baseItem(
        source = Source.ADD,
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        contentEditIcon = {
            Icon(
                painter = Resources.getIcon("add_circle"),
                contentDescription = Resources.getString("edit_add_button_content_description"),
                tint = Color.Green
            )
        },
        editModeActivated = true,
        onEditClick = onEditClick,
        contentName = {
            managerText(
                text = name
            )
        },

        ) {
        content()
    }

}

@Composable
private fun baseItem(
    source: Source,
    iconPainter: Painter,
    iconContentDescription: String,
    contentEditIcon: @Composable () -> Unit,
    onEditClick: () -> Unit,
    editModeActivated: Boolean,
    contentName: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    var modifier: Modifier

    Box(
        modifier = Modifier
    ) {

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

            val density = LocalDensity.current

            val finalWidth: MutableState<Dp> = remember { mutableStateOf(0.dp) }



            Column(
                modifier = Modifier
                    .padding(20.dp)

            ) {
                modifier = when (source) {
                    Source.ADD -> Modifier

                    Source.BODY -> Modifier.onGloballyPositioned {
                        finalWidth.value = density.run { it.size.width.toDp() }
                    }
                }
                Row(
                    modifier = modifier
                ) {
                    Icon(
                        painter = iconPainter,
                        contentDescription = iconContentDescription
                    )
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                    )

                    contentName()


                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                modifier = when (source) {
                    Source.ADD -> Modifier

                    Source.BODY -> Modifier.width(finalWidth.value)

                }

                Column(
                    modifier = modifier
                ) {
                    content()
                }
            }
        }




        if (editModeActivated) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {
                    onEditClick()
                }
            ) {
                contentEditIcon()
            }
        }

    }
}





