package ui.configuration.confDialogs

import FState
import UiState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import model.item.BaseI
import ui.component.managerNameTextField
import ui.dialogs.baseDialog
import ui.dialogs.baseDialogButton
import ui.dialogs.baseDialogText
import utils.Resources


private val viewModel = ConfVM()

@Composable
fun confNotSaveDialog() {
    baseDialog(
        enabled = FState.ui.dialogExpanded.value == UiState.Dialog.CONF_IS_NOT_SAVE,
        title = Resources.getString("dialog/title/conf_not_save"),
        onEnterKey = { FState.ui.dialogExpanded.value = UiState.Dialog.NEW_CONF },
        topContent = {
            baseDialogText(
                text = Resources.getString("dialog/conf_not_save")
            )
        },
        bottomContent = {
            baseDialogButton(
                onClick = {
                    FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                },
                text = Resources.getString("common/quit")
            )

            val confId = FState.settings.confId.value

            if (confId != null) {
                baseDialogButton(
                    onClick = {
                        if (!viewModel.saveConfiguration(FState.ui.confName.value)) {
                            println("ERROR: can't save config")
                            FState.ui.dialogExpanded.value = UiState.Dialog.NEW_CONF
                            return@baseDialogButton
                        }

                        FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                    },
                    text = Resources.getString("common/override")
                )
            }


            baseDialogButton(
                onClick = {
                    FState.ui.dialogExpanded.value = UiState.Dialog.NEW_CONF
                },
                text = Resources.getString("common/new")
            )
        }
    )
}


@Composable
fun newConfDialog() {
    val enabled = FState.ui.dialogExpanded.value == UiState.Dialog.NEW_CONF

    val configList = viewModel.settings.confInfoList
    val id = BaseI.getAvailableId(
        list = configList.map { it.id },
        prefix = "conf"
    )
    val text = remember(id) { mutableStateOf("") }

    baseDialog(
        enabled = enabled,
        title = Resources.getString("dialog/title/new_conf"),
        onEnterKey = {
            if (viewModel.addConfiguration(text.value, id))
                FState.ui.dialogExpanded.value = UiState.Dialog.NONE
        },
        topContent = {
            val focusRequester = remember { FocusRequester() }

            managerNameTextField(
                text = text,
                ids = Pair(id, null),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .widthIn(min = 200.dp, max = 250.dp)
                    .height(40.dp),
                onValueChange = {
                    BaseI.checkNameTaken(
                        names = configList.map { config ->
                            config.name.value
                        },
                        name = it
                    )
                },
                placeholder = Resources.getString("label/conf_name"),
            )
            if (enabled) {
                LaunchedEffect(
                    Unit
                ) {
                    delay(500L)
                    focusRequester.requestFocus()
                }
            }
        },
        bottomContent = {
            baseDialogButton(
                onClick = {
                    FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                },
                icon = Resources.getIcon("select/close/close24"),
                text = Resources.getString("common/cancel")
            )

            baseDialogButton(
                onClick = {
                    if (viewModel.addConfiguration(text.value, id))
                        FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                },
                icon = Resources.getIcon("select/check24"),
                text = Resources.getString("common/add")
            )
        }
    )
}