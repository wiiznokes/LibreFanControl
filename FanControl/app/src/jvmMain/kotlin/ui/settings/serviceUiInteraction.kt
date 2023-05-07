package ui.settings

import FState
import UiState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.settingSlidingWindows.SettingScope
import ui.dialogs.baseDialog
import ui.dialogs.baseDialogButton
import ui.dialogs.baseDialogText
import ui.theme.LocalColors
import utils.Resources


fun SettingScope.launchAtStartUp(
    launchAtStartUp: MutableState<Boolean>,
    onLaunchAtStartUpChange: (Boolean) -> Unit,
) {
    item(
        title = Resources.getString("settings/launch_at_start_up"),
        advanceIconButton = {
            Checkbox(
                checked = launchAtStartUp.value,
                onCheckedChange = {
                    onLaunchAtStartUpChange(it)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = LocalColors.current.input,
                    uncheckedColor = LocalColors.current.onInactiveInput,
                    checkmarkColor = LocalColors.current.onInput,
                )
            )
        },
        showTopLine = true
    )
}


@Composable
fun launchAtStartUpDialog(
    onLaunchAtStartUpChange: (Boolean) -> Unit,
) {
    baseDialog(
        enabled = FState.ui.dialogExpanded.value == UiState.Dialog.LAUNCH_AT_START_UP,
        title = Resources.getString("dialog/title/launch_at_start_up"),
        onEnterKey = { onLaunchAtStartUpChange(true) },
        topContent = {
            baseDialogText(
                text = Resources.getString("dialog/launch_at_start_up")
            )
        },
        bottomContent = {
            baseDialogButton(
                onClick = { FState.ui.dialogExpanded.value = UiState.Dialog.NONE },
                text = Resources.getString("common/no")
            )
            baseDialogButton(
                onClick = { onLaunchAtStartUpChange(true) },
                text = Resources.getString("common/yes"),
                containerColor = LocalColors.current.inputMain,
                contentColor = LocalColors.current.onInputMain,
            )
        }
    )
}


fun SettingScope.removeService(
    onRemoveService: () -> Unit,
) {
    item(
        title = Resources.getString("settings/remove_service"),
        onClick = onRemoveService,
        showAdvanceIcon = false
    )
}