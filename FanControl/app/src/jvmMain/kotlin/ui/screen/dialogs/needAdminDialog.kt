package ui.screen.dialogs

import FState
import UiState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import ui.utils.Resources

@Composable
fun needAdminDialog() {
    baseDialog(
        enabled = FState.ui.dialogExpanded.value == UiState.Dialog.NEED_ADMIN,
        title = Resources.getString("dialog/title/need_admin"),
        onEnterKey = {
            FState.ui.dialogExpanded.value = UiState.Dialog.NONE
        },
        topContent = {
            baseDialogText(
                text = Resources.getString("dialog/admin")
            )
        },
        bottomContent = {
            baseDialogButton(
                onClick = {
                    FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                },
                icon = Resources.getIcon("select/check24"),
                text = Resources.getString("common/ok")
            )
        },
        horizontalArrangement = Arrangement.End
    )
}