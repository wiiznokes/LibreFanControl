package ui.screen.drawer.secondView

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.drawer.SettingType
import ui.utils.Resources


@Composable
fun settingTimeUpdate(
    settingState: MutableState<SettingType>,
    onDelayChange: (Int) -> Unit,
    updateDelay: Int
) {

    baseSecondView(
        title = Resources.getString("settings/update_delay"),
        settingState = settingState
    ) {
        item {
            Spacer(Modifier.height(40.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                managerText(
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyLarge,
                    text = updateDelay.toString(),
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
                Column {
                    IconButton(
                        onClick = {
                            val newDelay = updateDelay + 1
                            if (isCorrectDelay(newDelay))
                                onDelayChange(newDelay)
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("sign/plus/add40"),
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
                            painter = Resources.getIcon("sign/minus/remove40"),
                            contentDescription = Resources.getString("ct/decrease"),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                }
            }

            Spacer(Modifier.height(80.dp))
        }
    }
}

private fun isCorrectDelay(delay: Int): Boolean =
    delay in 1..100