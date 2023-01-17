package ui.screen.drawer.secondView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources


/**
 * name need to respect the format in
 * json string file and configuration
 */
@Suppress("EnumEntryName")
enum class Languages {
    en,
    fr;

    companion object {
        infix fun from(value: String): Languages = Languages.values().first { it.name == value }
    }
}

@Composable
fun settingLanguage(
    onLanguageChange: (Languages) -> Unit
) {


    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.inverseOnSurface,
        thickness = 2.dp
    )

    LazyColumn {
        items(Languages.values()) {
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