package ui.settings

import FState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import settingSlidingWindows.SettingScope
import ui.component.managerText
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import utils.Resources


fun SettingScope.info() {
    item(
        title = Resources.getString("settings/info"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/info24"),
                tint = LocalColors.current.onSecondContainer,
                contentDescription = null
            )
        },
        showTopLine = false
    ) {
        header(null, null)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = LocalColors.current.onSecondContainer,
            thickness = 2.dp
        )
        Column {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LocalColors.current.secondContainer)
                ) {
                    managerText(
                        text = "App version: ${FState.appVersion}",
                        color = LocalColors.current.onSecondContainer,
                        modifier = Modifier.padding(LocalSpaces.current.medium)
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = LocalColors.current.onSecondContainer,
                    thickness = 2.dp
                )
            }
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LocalColors.current.secondContainer)
                ) {
                    managerText(
                        text = "Service version: ${FState.settings.versionInstalled.value}",
                        color = LocalColors.current.onSecondContainer,
                        modifier = Modifier.padding(LocalSpaces.current.medium)
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = LocalColors.current.onSecondContainer,
                    thickness = 2.dp
                )
            }

            Spacer(Modifier.height(80.dp))
        }

    }
}