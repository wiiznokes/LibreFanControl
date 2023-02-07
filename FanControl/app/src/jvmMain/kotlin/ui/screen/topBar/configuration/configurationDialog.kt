package ui.screen.topBar.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import model.item.BaseI
import model.item.BaseI.Companion.checkNameTaken
import ui.component.managerButton
import ui.component.managerNameTextField
import ui.theme.LocalColors
import ui.utils.Resources

@Composable
fun addConfiguration() {
    val dialogExpanded = remember { mutableStateOf(false) }

    /*
        used to know if add conf button should trigger
        because when the dialog appears, this button is
        still focused, and will trigger if Enter is pressed
    */
    val keyEnterPressed = remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            if (keyEnterPressed.value)
                keyEnterPressed.value = false
            else
                dialogExpanded.value = true
        }
    ) {
        Icon(
            painter = Resources.getIcon("sign/plus/add40"),
            contentDescription = null,
            tint = LocalColors.current.onMainTopBar
        )
    }


    dialog(
        enabled = dialogExpanded,
        keyEnterPressed = keyEnterPressed
    )

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun dialog(
    enabled: MutableState<Boolean>,
    keyEnterPressed: MutableState<Boolean>,
) {

    val configList = viewModel.settings.confInfoList

    val id = BaseI.getAvailableString(
        list = configList.map { it.id },
        prefix = "conf"
    )
    val text = remember(id) { mutableStateOf("") }


    Dialog(
        visible = enabled.value,
        resizable = false,
        onCloseRequest = {
            println("close dialog")
            enabled.value = false
        },
        title = Resources.getString("title/add_config"),
        focusable = enabled.value,
        onKeyEvent = {
            println("on key event, ${it.key}")
            when (it.key) {
                Key.Escape -> {
                    enabled.value = false
                    return@Dialog true
                }

                Key.Enter -> {
                    if (viewModel.addConfiguration(text.value, id)) {
                        keyEnterPressed.value = true
                        enabled.value = false
                    }
                    return@Dialog true
                }

                else -> return@Dialog false
            }
        }
    ) {

        val focusRequester = remember { FocusRequester() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalColors.current.mainContainer),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            managerNameTextField(
                text = text,
                ids = Pair(id, null),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .widthIn(min = 200.dp, max = 250.dp)
                    .height(40.dp),
                onValueChange = {
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name.value
                        },
                        name = it
                    )
                },
                placeholder = Resources.getString("label/conf_name"),
            )

            if (enabled.value) {
                LaunchedEffect(
                    Unit
                ) {
                    delay(500L)
                    focusRequester.requestFocus()
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                managerButton(
                    onClick = {
                        enabled.value = false
                        println("cancel conf")
                    },
                    icon = Resources.getIcon("select/close/close24"),
                    text = Resources.getString("common/cancel")
                )
                managerButton(
                    onClick = {
                        if (viewModel.addConfiguration(text.value, id))
                            enabled.value = false
                        println("add conf")
                    },
                    icon = Resources.getIcon("select/check24"),
                    text = Resources.getString("common/add"),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = LocalColors.current.error
                    )
                )
            }
        }
    }
}