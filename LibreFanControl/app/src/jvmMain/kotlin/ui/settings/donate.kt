package ui.settings

import Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import settingSlidingWindows.SettingScope
import ui.component.managerText
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import utils.Resources
import java.awt.Desktop
import java.net.URI


private data class Donate(
    val title: String,
    val uri: URI,
)

private val donateList = listOf(
    Donate(
        title = "Paypal",
        uri = URI("https://www.paypal.com/donate/?hosted_button_id=HV84HZ4G63HQ6")
    )
)


fun SettingScope.donate() {

    item(
        title = Resources.getString("settings/donate"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/attach_money24"),
                tint = LocalColors.current.onSecondContainer,
                contentDescription = null
            )
        },
        showTopLine = true
    ) {
        header(null, null)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = LocalColors.current.onSecondContainer,
            thickness = 2.dp
        )
        LazyColumn {
            items(donateList) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LocalColors.current.secondContainer)
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

        Spacer(Modifier.height(80.dp))
    }
}