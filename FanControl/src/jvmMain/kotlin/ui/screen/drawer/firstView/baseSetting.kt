package ui.screen.drawer.firstView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.IconButton
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import settings.SettingsModel
import ui.component.managerText
import ui.screen.drawer.SettingType
import ui.utils.Resources


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun settingFistView(
    drawerState: DrawerState,
    scope: CoroutineScope,
    settingType: MutableState<SettingType>,
    lazyListState: LazyListState,
    settings: SettingsModel
) {


    LazyColumn(
        state = lazyListState
    ) {
        topMainSetting(drawerState, scope)
        items(getMainItemSetting(settings)) {
            baseDefaultItemSetting(
                item = it,
                settingType = settingType
            )
        }

        baseTransition(Resources.getString("settings/trans/donate"))
        item {
            baseDonateItemSetting(
                item = getDonateItemSetting(),
                settingType = settingType
            )
        }

        baseTransition(Resources.getString("settings/trans/other"))
        items(getOtherItemSetting()) {
            baseDefaultItemSetting(
                item = it,
                settingType = settingType
            )
        }

        item {
            Spacer(Modifier.height(80.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
private fun LazyListScope.topMainSetting(
    drawerState: DrawerState,
    scope: CoroutineScope,
) {
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
                    painter = Resources.getIcon("arrow/arrow_back48"),
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
}


private fun LazyListScope.baseTransition(
    text: String
) {
    item {
        managerText(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier.padding(top = 25.dp, bottom = 5.dp)
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            thickness = 2.dp
        )
    }
}

@Composable
private fun baseDefaultItemSetting(
    item: SettingItem,
    settingType: MutableState<SettingType>
) {
    baseItemSetting(
        icon = item.icon,
        settingState = settingType,
        type = item.type
    ) {
        Column {
            managerText(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                overflow = TextOverflow.Clip,
                maxLines = 2
            )
            managerText(
                text = item.subTitle,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                overflow = TextOverflow.Clip,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun baseDonateItemSetting(
    item: DonateSettingItem,
    settingType: MutableState<SettingType>
) {
    baseItemSetting(
        modifier = Modifier
            .background(color = Color.Yellow),
        onColor = Color.Black,
        icon = item.icon,
        type = item.type,
        settingState = settingType
    ) {
        managerText(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}


@Composable
private fun baseItemSetting(
    modifier: Modifier = Modifier,
    onColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    icon: String,
    type: SettingType,
    settingState: MutableState<SettingType>,
    content: @Composable () -> Unit
) {
    Column {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier
                    .clickable(onClick = { settingState.value = type }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    IconButton(
                        onClick = { settingState.value = type }
                    ) {
                        Icon(
                            painter = Resources.getIcon("arrow/chevron/chevron_right40"),
                            contentDescription = Resources.getString("ct/open_setting_item"),
                            tint = onColor
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = Resources.getIcon(icon),
                            contentDescription = null,
                            tint = onColor
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        content()
                    }
                }
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = onColor,
            thickness = 2.dp
        )
    }
}