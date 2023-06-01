package ui.dialogs

import FState
import UiState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.rememberDialogState
import ui.component.managerText
import ui.theme.LocalColors
import ui.theme.LocalSpaces


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun baseDialog(
    enabled: Boolean,
    title: String,
    onEnterKey: () -> Unit,
    topContent: @Composable ColumnScope.() -> Unit,
    bottomContent: @Composable RowScope.() -> Unit,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceAround,
) {
    Dialog(
        state = rememberDialogState(
            size = DpSize(width = 500.dp, height = 350.dp)
        ),
        visible = enabled,
        resizable = true,
        onCloseRequest = {
            //println("close dialog")
            FState.ui.dialogExpanded.value = UiState.Dialog.NONE
        },
        title = title,
        focusable = true,
        onKeyEvent = {
            //println("on key event, ${it.key}")
            when (it.key) {
                Key.Escape -> {
                    FState.ui.dialogExpanded.value = UiState.Dialog.NONE
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
    Surface(
        color = LocalColors.current.mainSurface,
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .fillMaxHeight(0.6f)
            .padding(LocalSpaces.current.medium),
    ) {
        Text(
            modifier = modifier
                .scrollable(rememberScrollState(0), orientation = Orientation.Vertical)
                .padding(LocalSpaces.current.medium),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = LocalColors.current.onMainSurface
        )
    }
}

@Composable
fun baseDialogButton(
    modifier: Modifier = Modifier
        .width(80.dp),
    onClick: () -> Unit,
    text: String,
    icon: Painter? = null,
    containerColor: Color = LocalColors.current.inputVariant,
    contentColor: Color = LocalColors.current.onInputVariant,
) {


    Button(
        modifier = Modifier
            .padding(LocalSpaces.current.medium),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        )
    ) {

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            managerText(
                text = text,
                color = contentColor
            )

            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = contentColor
                )
            }
        }
    }
}