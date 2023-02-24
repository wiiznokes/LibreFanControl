package ui.screen.dialog

import FState
import UiState
import androidx.compose.runtime.Composable
import ui.screen.topBar.configuration.ConfigurationVM
import ui.utils.Resources


private val viewModel = ConfigurationVM()

@Composable
fun confNotSaveDialog() {
    baseDialog(
        enabled = FState.ui.dialogExpanded.value == UiState.Dialog.CONF_IS_NOT_SAVE,
        title = Resources.getString("dialog/title/conf_not_save"),
        onEnterKey = { FState.ui.dialogExpanded.value = UiState.Dialog.SAVE_CONF },
        topContent = {
            baseDialogText(
                text = Resources.getString("dialog/conf_not_save")
            )
        },
        bottomContent = {
            baseDialogButton(
                onClick = { },
                text = Resources.getString("common/quit")
            )

            val confId = FState.settings.confId.value

            if (confId != null) {
                baseDialogButton(
                    onClick = {
                        if (!viewModel.saveConfiguration(FState.ui.confName.value)) {
                            println("ERROR: can't save config")
                            FState.ui.dialogExpanded.value = UiState.Dialog.SAVE_CONF
                            return@baseDialogButton
                        }

                        FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                    },
                    text = Resources.getString("common/override")
                )
            }


            baseDialogButton(
                onClick = {
                    FState.ui.dialogExpanded.value = UiState.Dialog.SAVE_CONF
                },
                text = Resources.getString("common/new")
            )
        }
    )
}