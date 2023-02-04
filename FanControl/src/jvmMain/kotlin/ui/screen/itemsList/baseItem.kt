package ui.screen.itemsList


import State
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import model.item.BaseItem
import ui.component.managerNameTextField
import ui.component.managerText
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources


@Composable
fun baseItemBody(
    icon: Painter,
    item: BaseItem,
    onNameChange: (String) -> Unit,
    onEditClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    baseItem(
        color = LocalColors.current.mainContainer,
        onColor = LocalColors.current.onMainContainer,
        editModeActivated = State.editModeActivated.collectAsState().value,
        icon = icon,
        editIcon = {
            Icon(
                painter = Resources.getIcon("select/close/close24"),
                tint = Color.White,
                contentDescription = null
            )
        },
        editIconContainerColor = Color.Red,
        onEditClick = onEditClick,
        contentName = {
            managerNameTextField(
                value = item.name,
                ids = Pair(item.id, State.settings.collectAsState().value.configId),
                onValueChange = { onNameChange(it) },
                placeholder = Resources.getString("label/name"),
            )
        }
    ) {
        content()
    }
}

@Composable
fun baseItemAddItem(
    icon: Painter,
    name: String,
    onEditClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    baseItem(
        icon = icon,
        contentName = {
            managerText(
                text = name,
                color = LocalColors.current.onSecondContainer
            )
        },
        color = LocalColors.current.secondContainer,
        onColor = LocalColors.current.onSecondContainer,
        editIcon = {
            Icon(
                painter = Resources.getIcon("sign/plus/add24"),
                contentDescription = null,
                tint = Color.White
            )
        },
        editIconContainerColor = Color.Green,
        editModeActivated = true,
        onEditClick = onEditClick,
    ) {
        content()
    }

}

@Composable
private fun baseItem(
    icon: Painter,
    contentName: @Composable RowScope.() -> Unit,
    color: Color,
    onColor: Color,
    editIcon: @Composable () -> Unit,
    editIconContainerColor: Color,
    editModeActivated: Boolean,
    onEditClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Box {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .padding(10.dp),
            shape = MaterialTheme.shapes.medium,
            color = color,
            border = BorderStroke(
                width = 2.dp,
                color = onColor
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(LocalSpaces.current.large)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = onColor
                    )
                    Spacer(Modifier.width(LocalSpaces.current.medium))

                    contentName()
                }

                Spacer(Modifier.height(LocalSpaces.current.large))

                Column {
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
                containerColor = editIconContainerColor,
            ) {
                editIcon()
            }
        }

    }
}





