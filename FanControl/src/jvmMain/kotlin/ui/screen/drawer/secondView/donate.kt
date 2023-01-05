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
import java.awt.Desktop
import java.net.URI


private data class Donate(
    val title: String,
    val uri: URI
)

private val donateList = listOf(
    Donate(
        title = "Paypal",
        uri = URI("https://www.paypal.com/donate/?hosted_button_id=HV84HZ4G63HQ6")
    )
)


@Composable
fun settingDonate(
    settingState: MutableState<SettingType>
) {
    baseSecondView(
        title = Resources.getString("settings/donate"),
        settingState = settingState
    ) {
        item {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.inverseOnSurface,
                thickness = 2.dp
            )
        }
        items(donateList) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val desktop = Desktop.getDesktop()

                            try {
                                desktop.browse(it.uri)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                ) {
                    managerText(
                        text = it.title,
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