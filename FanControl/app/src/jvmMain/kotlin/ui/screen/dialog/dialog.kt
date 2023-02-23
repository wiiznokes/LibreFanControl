package ui.screen.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Dialog
import ui.theme.LocalColors
import ui.utils.Resources


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun baseDialog(
    enabled: MutableState<Boolean>,
    title: String,
    onEnterKey: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
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
            content()
        }
    }
}



@Composable
fun needAdminDialog() {



    baseDialog(
        enabled = State.ui.adminDialogExpanded,
        title = Resources.getString("dialog/name/need_admin"),
        onEnterKey = { }
    ) {

    }
}