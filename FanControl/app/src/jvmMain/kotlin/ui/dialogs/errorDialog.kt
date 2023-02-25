package ui.dialogs

import FState
import UiState
import androidx.compose.runtime.Composable
import ui.theme.LocalColors
import utils.Resources
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

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
                    setClipboard(FState.ui.errorDialogContent.value)
                },
                text = Resources.getString("common/copy")
            )
            baseDialogButton(
                onClick = { FState.ui.closeShowError() },
                text = Resources.getString("common/ok"),
                containerColor = LocalColors.current.inputMain,
                contentColor = LocalColors.current.onInputMain,
            )
        }
    )
}


private fun setClipboard(s: String) {
    val selection = StringSelection(s)
    val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(selection, selection)
}