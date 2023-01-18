package ui.screen.drawer.secondView

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.settingSlidingWindows.SettingScope
import ui.component.managerText
import ui.utils.Resources


fun SettingScope.updateDelay(
    onDelayChange: (Int) -> Unit,
    updateDelay: Int
) {

    item(
        content = {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                thickness = 2.dp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp),
                        painter = Resources.getIcon("settings/history40"),
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        contentDescription = null
                    )
                    managerText(
                        text = Resources.getString("settings/update_delay") + updateDelay,
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Clip,
                        maxLines = 2,
                        color = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }

                Column {
                    IconButton(
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
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                thickness = 2.dp
            )
        }
    )
}

private fun isCorrectDelay(delay: Int): Boolean =
    delay in 1..100