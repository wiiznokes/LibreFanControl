package ui.screen.topBar.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import ui.component.managerNameOutlinedTextField
import ui.component.managerText
import ui.utils.Resources
import utils.checkNameTaken
import utils.getAvailableId


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
            painter = Resources.getIcon("add"),
            contentDescription = Resources.getString("ct/add_conf"),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }

    if (dialogExpanded.value) {
        dialog(
            enabled = dialogExpanded,
            keyEnterPressed = keyEnterPressed
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun dialog(
    enabled: MutableState<Boolean>,
    keyEnterPressed: MutableState<Boolean>
) {

    val configList = viewModel.settings.value.configList

    val id = getAvailableId(configList.map { it.id })
    val text = remember(id) { mutableStateOf("") }

    Dialog(
        visible = enabled.value,
        resizable = false,
        onCloseRequest = { enabled.value = false },
        title = Resources.getString("title/add_config"),
        focusable = enabled.value,
        onKeyEvent = {
            when (it.key) {
                Key.Escape -> {
                    enabled.value = false
                    return@Dialog true
                }

                Key.Enter -> {
                    if (viewModel.addConfiguration(
                            name = text.value,
                            id = id
                        )
                    ) {
                        keyEnterPressed.value = true
                        enabled.value = false
                    }
                    return@Dialog true
                }

                else -> {
                    return@Dialog false
                }
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseSurface),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val focusRequester = remember { FocusRequester() }

            managerNameOutlinedTextField(
                value = text.value,
                ids = Pair(id, null),
                text = text,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .focusRequester(focusRequester),
                onValueChange = {
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name
                        },
                        name = it
                    )
                },
                label = Resources.getString("label/conf_name"),
            )
            LaunchedEffect(
                Unit
            ) {
                if (enabled.value) {
                    delay(400L)
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
                    },
                    text = Resources.getString("cancel")
                )
                managerButton(
                    onClick = {
                        if (viewModel.addConfiguration(
                                name = text.value,
                                id = id
                            )
                        ) {
                            enabled.value = false
                        }
                    },
                    text = Resources.getString("add")
                )
            }
        }
    }
}


@Composable
private fun managerButton(
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        managerText(
            text = text,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}