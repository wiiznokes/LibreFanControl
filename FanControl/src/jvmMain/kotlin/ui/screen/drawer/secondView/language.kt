package ui.screen.drawer.secondView

import androidx.compose.foundation.clickable
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
import ui.utils.Resources


/**
 * name need to respect the format in
 * json string file and configuration
 */
@Suppress("EnumEntryName")
enum class Languages {
    en;

    companion object {
        infix fun from(value: String): Languages = Languages.values().first { it.name == value }
    }
}

private val languages = listOf(
    Languages.en
)

@Composable
fun settingLanguage(
    settingState: MutableState<SettingType>,
    onLanguageChange: (Languages) -> Unit
) {
    baseSecondView(
        title = Resources.getString("settings/language"),
        settingState = settingState
    ) {
        item {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                thickness = 2.dp
            )
        }
        items(languages) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onLanguageChange(it)
                        }
                ) {
                    managerText(
                        text = Resources.getString("language/$it"),
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