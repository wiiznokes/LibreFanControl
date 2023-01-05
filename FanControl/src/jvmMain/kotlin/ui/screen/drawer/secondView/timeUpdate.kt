package ui.screen.drawer.secondView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.drawer.SettingType
import ui.utils.Resources

@Composable
fun settingTimeUpdate(
    settingState: MutableState<SettingType>
) {
    baseSecondView(
        title = "timeUpdate",
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
                    text = "2",
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
                Column {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = Resources.getIcon("add"),
                            contentDescription = Resources.getString("ct/increase"),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = Resources.getIcon("remove"),
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