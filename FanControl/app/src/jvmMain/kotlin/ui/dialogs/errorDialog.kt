package ui.dialogs

import FState
import UiState
import androidx.compose.runtime.Composable
import utils.Resources

@Composable
fun errorDialog() {
    baseDialog(
        enabled = FState.ui.dialogExpanded.value == UiState.Dialog.SHOW_ERROR,
        title = Resources.getString("dialog/title/error"),
        onEnterKey = { FState.ui.closeShowError() },
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
                onClick = { FState.ui.closeShowError() },
                text = Resources.getString("common/ok")
            )
        }
    )
}