package ui.screen.drawer.secondView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.settingSlidingWindows.SettingColors
import com.example.settingSlidingWindows.SettingScope
import ui.component.managerText
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


fun SettingScope.donate() {

    item(
        settingColors = SettingColors(
            container = Color.Yellow,
            onContainer = Color.Black
        ),
        title = Resources.getString("settings/donate"),
        icon = {
            Icon(
                painter = Resources.getIcon("settings/attach_money40"),
                tint = Color.Black,
                contentDescription = null
            )
        }
    ) {
        Header(null, null)

        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.inverseOnSurface,
            thickness = 2.dp
        )

        donateList.forEach {
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
        Spacer(Modifier.height(80.dp))
    }

}