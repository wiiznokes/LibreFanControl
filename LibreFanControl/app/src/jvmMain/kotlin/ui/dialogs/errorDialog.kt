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
    val error = FState.ui.errorDialog.value


    baseDialog(
        enabled = FState.ui.dialogExpanded.value == UiState.Dialog.SHOW_ERROR,
        title = Resources.getString("dialog/title/error"),
        onEnterKey = { FState.ui.closeShowError() },
        topContent = {

            if (error != null) {
                baseDialogText(
                    text = error.content
                )
            }

        },
        bottomContent = {

            if (error?.copyContent != null) {
                baseDialogButton(
                    onClick = {
                        println("copy")
                        setClipboard(error.copyContent)
                    },
                    text = Resources.getString("common/copy")
                )
            }

            baseDialogButton(
                onClick = { FState.ui.closeShowError() },
                icon = Resources.getIcon("select/check24"),
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