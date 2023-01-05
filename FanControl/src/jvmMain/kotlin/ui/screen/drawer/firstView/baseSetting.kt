package ui.screen.drawer.firstView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ui.component.managerText
import ui.screen.drawer.SettingType
import ui.utils.Resources

interface Info
data class DonateSettingItem(
    val title: String,
    val icon: String,
    val contentDescription: String
)

data class SettingItem(
    val title: String,
    val subTitle: String,
    val icon: String,
    val contentDescription: String,
    val type: SettingType = SettingType.UNSPECIFIED
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun baseSetting(
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: LazyListScope.()->Unit
) {
    LazyColumn {
        item {
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
                        painter = Resources.getIcon("arrow_back"),
                        contentDescription = Resources.getString("ct/close_drawer"),
                        tint = MaterialTheme.colorScheme.inverseOnSurface
                    )
                }
            }
        }
        item {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                thickness = 2.dp
            )
        }
        content()
    }
}

@Composable
fun baseSettingItem(
    item: SettingItem,
    onClick: () -> Unit
) {
    Column {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                Modifier
                    .clickable(
                        onClick = onClick
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    IconButton(
                        onClick = onClick
                    ) {
                        Icon(
                            painter = Resources.getIcon("chevron_right"),
                            contentDescription = Resources.getString("ct/open_setting_item"),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = Resources.getIcon(item.icon),
                            contentDescription = item.contentDescription,
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            )

                            Text(
                                text = item.subTitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        }
                    }
                }
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            thickness = 2.dp
        )
    }
}