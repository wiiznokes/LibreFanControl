package ui.screen.itemsList


import Source
import State
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.max
import model.ItemType
import model.item.BaseItem
import ui.component.managerNameOutlinedTextField
import ui.component.managerText
import ui.utils.Resources


@Composable
fun baseItemBody(
    iconPainter: Painter,
    iconContentDescription: String,
    item: BaseItem,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val configId = State.settings.collectAsState().value.configId

    baseItem(
        color = MaterialTheme.colorScheme.surface,
        onColor = MaterialTheme.colorScheme.onSurface,
        source = Source.BODY,
        type = item.type,
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        contentEditIcon = {
            Icon(
                painter = Resources.getIcon("select/close/close24"),
                contentDescription = Resources.getString("ct/edit_remove"),
                tint = Color.White
            )
        },
        editButtonContainerColor = Color.Red,
        editModeActivated = State.editModeActivated.collectAsState().value,
        onEditClick = onEditClick,
        contentName = {
            managerNameOutlinedTextField(
                value = item.name,
                ids = Pair(item.id, configId),
                onValueChange = { onNameChange(it) },
                label = Resources.getString("label/name"),
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
    type: ItemType,
    content: @Composable ColumnScope.() -> Unit
) {

    baseItem(
        color = MaterialTheme.colorScheme.surfaceVariant,
        onColor = MaterialTheme.colorScheme.onSurfaceVariant,
        source = Source.ADD,
        type = type,
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        contentEditIcon = {
            Icon(
                painter = Resources.getIcon("sign/plus/add24"),
                contentDescription = Resources.getString("ct/edit_add"),
                tint = Color.White
            )
        },
        editButtonContainerColor = Color.Green,
        editModeActivated = true,
        onEditClick = onEditClick,
        contentName = {
            managerText(
                text = name,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },

        ) {
        content()
    }

}

@Composable
private fun baseItem(
    color: Color,
    onColor: Color,
    source: Source,
    type: ItemType,
    iconPainter: Painter,
    iconContentDescription: String,
    contentEditIcon: @Composable () -> Unit,
    onEditClick: () -> Unit,
    editButtonContainerColor: Color,
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
                .padding(10.dp),
            shape = MaterialTheme.shapes.medium,
            color = color,
            border = BorderStroke(
                width = 2.dp,
                color = onColor
            )
        ) {

            val density = LocalDensity.current
            val finalWidth: MutableState<Dp> = remember { mutableStateOf(0.dp) }
            val minDp = when (type) {
                is ItemType.ControlType -> 200.dp
                is ItemType.BehaviorType -> 220.dp
                ItemType.SensorType.I_S_CUSTOM_TEMP -> 200.dp
                else -> 100.dp
            }

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                modifier = when (source) {
                    Source.ADD -> Modifier

                    Source.BODY -> Modifier.onGloballyPositioned {
                        finalWidth.value = max(minDp, density.run { it.size.width.toDp() })
                    }

                }
                Row(
                    modifier = modifier
                ) {
                    Icon(
                        painter = iconPainter,
                        contentDescription = iconContentDescription,
                        tint = onColor
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
            FloatingActionButton(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopEnd)
                    .requiredSize(30.dp),
                onClick = onEditClick,

                shape = RoundedCornerShape(100),
                containerColor = editButtonContainerColor,
            ) {
                contentEditIcon()
            }
        }

    }
}





