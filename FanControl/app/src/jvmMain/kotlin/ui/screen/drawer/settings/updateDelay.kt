package ui.screen.drawer.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settingSlidingWindows.SettingScope
import ui.utils.Resources


fun SettingScope.updateDelay(
    onDelayChange: (Int) -> Unit,
    updateDelay: Int,
) {
    item(
        icon = {
            Icon(
                painter = Resources.getIcon("settings/history40"),
                tint = MaterialTheme.colorScheme.inverseOnSurface,
                contentDescription = null
            )
        },
        title = Resources.getString("settings/update_delay") + updateDelay,
        advanceIconButton = {
            Column {
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        val newDelay = updateDelay + 1
                        if (isCorrectDelay(newDelay))
                            onDelayChange(newDelay)
                    }
                ) {
                    Icon(
                        painter = Resources.getIcon("sign/plus/add24"),
                        contentDescription = Resources.getString("ct/increase"),
                        tint = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = {
                        val newDelay = updateDelay - 1
                        if (isCorrectDelay(newDelay))
                            onDelayChange(newDelay)
                    }
                ) {
                    Icon(
                        painter = Resources.getIcon("sign/minus/remove24"),
                        contentDescription = Resources.getString("ct/decrease"),
                        tint = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }
            }
        },
        showTopLine = true
    )
}

private fun isCorrectDelay(delay: Int): Boolean =
    delay in 1..100