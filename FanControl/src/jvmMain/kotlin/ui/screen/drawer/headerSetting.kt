package ui.screen.drawer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.component.managerText
import ui.utils.Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun headerSetting(
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            managerText(
                modifier = Modifier
                    .padding(start = 25.dp, top = 40.dp, bottom = 50.dp),
                text = Resources.getString("title/setting"),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                style = MaterialTheme.typography.titleLarge
            )

            IconButton(
                onClick = { scope.launch { drawerState.close() } }
            ) {
                Icon(
                    painter = Resources.getIcon("arrow/arrow_back48"),
                    contentDescription = Resources.getString("ct/close_drawer"),
                    tint = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    }
}