package ui.component


import Source
import State
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
import androidx.compose.ui.unit.max
import kotlinx.coroutines.flow.asStateFlow
import model.ItemType
import model.item.BaseItem
import ui.utils.Resources


@Composable
fun baseItemBody(
    iconPainter: Painter,
    iconContentDescription: String,
    item: BaseItem,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    // id use to update value witch use remember
    val idConfig = State._idConfig.asStateFlow().value.value

    val text = remember(
        item.itemId, idConfig
    ) {
        mutableStateOf(item.name)
    }

    baseItem(
        source = Source.BODY,
        type = item.type,
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        contentEditIcon = {
            Icon(
                painter = Resources.getIcon("cancel"),
                contentDescription = Resources.getString("ct/edit_remove"),
                tint = Color.Red
            )
        },
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        contentName = {
            managerNameOutlinedTextField(
                modifier = Modifier
                    .widthIn(min = 90.dp, max = 180.dp)
                    .width(IntrinsicSize.Min)
                    .height(50.dp),
                text = text,
                ids = Pair(item.itemId, idConfig),
                onValueChange = {
                    onNameChange(it)
                },
                label = Resources.getString("label/name")
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
        source = Source.ADD,
        type = type,
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        contentEditIcon = {
            Icon(
                painter = Resources.getIcon("add_circle"),
                contentDescription = Resources.getString("ct/edit_add"),
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
    type: ItemType,
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
            val minDp = when (type) {
                is ItemType.ControlType -> 200.dp
                is ItemType.BehaviorType -> 220.dp
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





