package ui.screen.drawer.secondView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.IconButton
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources

@Composable
fun baseSecondView(
    title: String,
    content: LazyListScope.() -> Unit
) {
    LazyColumn {
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = Resources.getIcon("chevron_left"),
                            contentDescription = Resources.getString("ct/close_setting_item"),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                    managerText(
                        text = title,
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                    thickness = 2.dp
                )
                Spacer(Modifier.height(30.dp))
            }
        }
        content()
    }
}