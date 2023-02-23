package ui.screen.dialog

import FState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ui.component.managerText
import ui.theme.LocalColors
import ui.utils.Resources


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun baseDialog(
    enabled: MutableState<Boolean>,
    title: String,
    onEnterKey: () -> Unit,
    topContent: @Composable ColumnScope.() -> Unit,
    bottomContent: @Composable RowScope.() -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
) {
    Dialog(
        visible = enabled.value,
        resizable = false,
        onCloseRequest = {
            println("close dialog")
            enabled.value = false
        },
        title = title,
        focusable = enabled.value,
        onKeyEvent = {
            //println("on key event, ${it.key}")
            when (it.key) {
                Key.Escape -> {
                    enabled.value = false
                    return@Dialog true
                }

                Key.Enter -> {
                    onEnterKey()
                    return@Dialog true
                }

                else -> return@Dialog false
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalColors.current.mainContainer),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            topContent()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = horizontalArrangement
            ) {
                bottomContent()
            }
        }
    }
}


@Composable
fun baseDialogText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .fillMaxWidth(0.7f)
            .fillMaxHeight(0.6f),
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun baseDialogButton(
    modifier: Modifier = Modifier
        .width(80.dp),
    onClick: () -> Unit,
    text: String,
    icon: Painter? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = LocalColors.current.inputVariant
    ),
) {

    Button(
        onClick = onClick,
        colors = colors
    ) {

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            managerText(
                text = text,
                color = LocalColors.current.onInputVariant
            )

            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = LocalColors.current.onInputVariant
                )
            }
        }
    }
}


@Composable
fun needAdminDialog() {
    baseDialog(
        enabled = FState.ui.adminDialogExpanded,
        title = Resources.getString("dialog/title/need_admin"),
        onEnterKey = {
            FState.ui.adminDialogExpanded.value = false
        },
        topContent = {
            baseDialogText(
                text = Resources.getString("dialog/admin")
            )
        },
        bottomContent = {
            baseDialogButton(
                onClick = {
                    FState.ui.adminDialogExpanded.value = false
                },
                icon = Resources.getIcon("select/check24"),
                text = Resources.getString("common/ok")
            )
        },
        horizontalArrangement = Arrangement.End
    )
}


@Composable
fun errorDialog() {
    baseDialog(
        enabled = FState.ui.errorDialogExpanded,
        title = Resources.getString("dialog/title/error"),
        onEnterKey = {
            FState.ui.errorDialogExpanded.value = false
            FState.ui.errorDialogContent.value = ""
        },
        topContent = {
            baseDialogText(
                text = FState.ui.errorDialogContent.value
            )
        },
        bottomContent = {
            baseDialogButton(
                onClick = {
                    println("copy")
                },
                text = Resources.getString("common/copy")
            )
            baseDialogButton(
                onClick = {
                    FState.ui.errorDialogExpanded.value = false
                    FState.ui.errorDialogContent.value = ""
                },
                text = Resources.getString("common/copy")
            )
        }
    )
}