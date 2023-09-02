package ui.settings

import FState
import UiState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import settingSlidingWindows.SettingScope
import ui.dialogs.baseDialog
import ui.dialogs.baseDialogButton
import ui.dialogs.baseDialogText
import ui.theme.LocalColors
import ui.theme.LocalSpaces
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
                onClick = {
                    onLaunchAtStartUpChange(true)
                    // means change is successful, in case there is an error, we don't close the dialog
                    if (FState.ui.dialogExpanded.value == UiState.Dialog.LAUNCH_AT_START_UP) {
                        FState.ui.dialogExpanded.value = UiState.Dialog.NONE
                    }
                },
                text = Resources.getString("common/yes"),
                containerColor = LocalColors.current.inputMain,
                contentColor = LocalColors.current.onInputMain,
            )
        }
    )
}


fun SettingScope.installService(
    installService: () -> Unit,
) {
    item(
        title = Resources.getString("settings/install_service"),
        onClick = installService,
        showAdvanceIcon = false
    )
}


fun SettingScope.startService(
    startService: () -> Unit,
) {
    item(
        title = Resources.getString("settings/start_service"),
        onClick = startService,
        showAdvanceIcon = false
    )
}

fun SettingScope.openService(
    openService: () -> Unit,
) {
    item(
        title = Resources.getString("settings/open_service"),
        onClick = openService,
        showAdvanceIcon = false
    )
}

fun SettingScope.uninstallService(
    onRemoveService: () -> Unit,
) {
    item(
        title = Resources.getString("settings/uninstall_service"),
        onClick = onRemoveService,
        showAdvanceIcon = false
    )
}


fun SettingScope.valueDisableControl(
    onValueDisableControl: (Int) -> Unit,
) {
    val valueDisableControl = FState.settings.valueDisableControl
    item(
        title = "${Resources.getString("settings/value_disable_control")}: ${valueDisableControl.value}",
        subTitle = Resources.getString("settings/value_disable_control_sub_title"),
        advanceIconButton = {
            Column(
                modifier = Modifier
                    .padding(end = LocalSpaces.current.large)
                    .padding(vertical = LocalSpaces.current.small)
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        onValueDisableControl(valueDisableControl.value + 1)
                    },
                    painter = Resources.getIcon("sign/plus/add20"),
                    contentDescription = Resources.getString("ct/increase"),
                    tint = LocalColors.current.onSecondContainer
                )

                Icon(
                    modifier = Modifier.clickable {
                        onValueDisableControl(valueDisableControl.value - 1)
                    },
                    painter = Resources.getIcon("sign/minus/remove20"),
                    contentDescription = Resources.getString("ct/decrease"),
                    tint = LocalColors.current.onSecondContainer
                )
            }
        }
    )
}