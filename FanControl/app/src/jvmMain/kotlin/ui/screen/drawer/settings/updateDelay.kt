package ui.screen.drawer.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.settingSlidingWindows.SettingScope
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources


fun SettingScope.updateDelay(
    onDelayChange: (Int) -> Unit,
    updateDelay: MutableState<Int>,
) {
    item(
        icon = {
            Icon(
                painter = Resources.getIcon("settings/update24"),
                tint = LocalColors.current.onSecondContainer,
                contentDescription = null
            )
        },
        title = Resources.getString("settings/update_delay") + updateDelay.value,
        advanceIconButton = {
            Column(
                modifier = Modifier
                    .padding(end = LocalSpaces.current.large)
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        onDelayChange(updateDelay.value + 1)
                    },
                    painter = Resources.getIcon("sign/plus/add20"),
                    contentDescription = Resources.getString("ct/increase"),
                    tint = LocalColors.current.onSecondContainer
                )

                Icon(
                    modifier = Modifier.clickable {
                        onDelayChange(updateDelay.value - 1)
                    },
                    painter = Resources.getIcon("sign/minus/remove20"),
                    contentDescription = Resources.getString("ct/decrease"),
                    tint = LocalColors.current.onSecondContainer
                )
            }
        },
        showTopLine = true
    )
}