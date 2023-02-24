package ui.screen.dialogs

import FState
import UiState
import androidx.compose.runtime.Composable
import ui.utils.Resources

@Composable
fun launchAtStartUpDialog() {
    baseDialog(
        enabled = FState.ui.dialogExpanded.value == UiState.Dialog.LAUNCH_AT_START_UP,
        title = Resources.getString("dialog/title/launch_at_start_up"),
        onEnterKey = {
            FState.ui.dialogExpanded.value = UiState.Dialog.NONE
        },
        topContent = {
            baseDialogText(
                text = Resources.getString("dialog/launch_at_start_up")
            )
        },
        bottomContent = {
            baseDialogButton(
                onClick = {
                    FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                },
                text = Resources.getString("common/no")
            )
            baseDialogButton(
                onClick = {
                    FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                },
                text = Resources.getString("common/yes")
            )
        }
    )
}