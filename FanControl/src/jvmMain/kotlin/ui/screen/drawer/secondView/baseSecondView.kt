package ui.screen.drawer.secondView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
fun baseSecondView(
    title: String,
    settingState: MutableState<SettingType>,
    content: LazyListScope.() -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            IconButton(
                onClick = { settingState.value = SettingType.FIRST_VIEW }
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterStart),
                    painter = Resources.getIcon("arrow/chevron/chevron_left"),
                    contentDescription = Resources.getString("ct/close_setting_item"),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
            managerText(
                modifier = Modifier.align(Alignment.Center),
                text = title,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Divider(
            modifier = Modifier.fillMaxWidth(0.95f),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            thickness = 2.dp
        )
        Spacer(Modifier.height(40.dp))
    }

    LazyColumn {
        content()
    }
}