package ui.screen.drawer.secondView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.settingSlidingWindows.SettingScope
import ui.component.managerText
import ui.utils.Resources

@OptIn(ExperimentalMaterial3Api::class)
fun SettingScope.launchAtStartUp(
    launchAtStartUp: Boolean,
    onLaunchAtStartUpChange: (Boolean) -> Unit
) {

    item(
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.inverseSurface
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                managerText(
                    text = Resources.getString("settings/launch_at_startup"),
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
                Checkbox(
                    checked = launchAtStartUp,
                    onCheckedChange = {
                        onLaunchAtStartUpChange(it)
                    }
                )
            }
        }
    )
}