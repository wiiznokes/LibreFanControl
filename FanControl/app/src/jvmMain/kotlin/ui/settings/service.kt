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
import com.example.settingSlidingWindows.SettingScope
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

fun SettingScope.tryOpenService(
    onTryOpenService: () -> Unit,
) {
    item(
        title = Resources.getString("settings/try_open_service"),
        onClick = onTryOpenService,
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