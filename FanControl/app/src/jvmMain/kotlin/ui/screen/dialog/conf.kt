package ui.screen.dialog

import UiState
import androidx.compose.runtime.Composable
import proto.ConfHelper
import proto.SettingsHelper
import ui.screen.topBar.configuration.ConfigurationVM
import ui.utils.Resources


private val viewModel = ConfigurationVM()

@Composable
fun confNotSaveDialog(
    onQuit: () -> Unit,
    confName: String
) {
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
                onClick = { onQuit() },
                text = Resources.getString("common/quit")
            )

            val confId = FState.settings.confId.value

            if (confId != null) {
                baseDialogButton(
                    onClick = {
                        if (!viewModel.saveConfiguration(confName))

                        FState.ui.confIsNotSaveDialogExpanded.value = false
                        onQuit()
                    },
                    text = Resources.getString("common/override")
                )
            }


            baseDialogButton(
                onClick = {
                    FState.ui.confIsNotSaveDialogExpanded.value = false
                    FState.ui.saveConfDialogExpanded.value = true
                },
                text = Resources.getString("common/new")
            )
        }
    )
}