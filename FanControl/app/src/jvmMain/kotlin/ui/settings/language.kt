package ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.settingSlidingWindows.SettingScope
import ui.component.managerText
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import utils.Resources


fun SettingScope.language(
    language: MutableState<Languages>,
    onLanguageChange: (Languages) -> Unit,
) {
    item(
        title = Resources.getString("settings/language"),
        subTitle = Resources.getString("language/${language.value}"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/translate24"),
                tint = LocalColors.current.onSecondContainer,
                contentDescription = null
            )
        }
    ) {
        header(null, null)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = LocalColors.current.onSecondContainer,
            thickness = 2.dp
        )

        LazyColumn {
            items(Languages.values()) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLanguageChange(it) }
                            .background(color = LocalColors.current.secondContainer)
                    ) {
                        managerText(
                            text = Resources.getString("language/$it"),
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
            }

            item {
                Spacer(Modifier.height(80.dp))
            }
        }
    }

}