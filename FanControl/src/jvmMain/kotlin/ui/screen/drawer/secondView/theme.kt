package ui.screen.drawer.secondView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.drawer.SettingType


val themes = listOf(
    "system", "dark", "light"
)


@Composable
fun settingTheme(
    settingState: MutableState<SettingType>
) {
    baseSecondView(
        title = "theme",
        settingState = settingState
    ) {
        item {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                thickness = 2.dp
            )
        }
        items(themes) {
            Column {
                Row {
                    managerText(
                        text = it,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    thickness = 2.dp
                )
            }
        }
        item {
            Spacer(Modifier.height(80.dp))
        }
    }
}