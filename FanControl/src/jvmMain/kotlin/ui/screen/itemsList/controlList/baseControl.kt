package ui.screen.itemsList.controlList


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources


@Composable
fun baseControl(
    isAuto: Boolean,
    switchEnabled: Boolean,
    onSwitchClick: (Boolean) -> Unit,
    value: Int,
    fanValue: Int,
    color: Color,
    contentListChoice: @Composable () -> Unit
) {

    Row {

        Switch(
            enabled = switchEnabled,
            checked = !isAuto,
            onCheckedChange = {
                onSwitchClick(it)
            },
            colors = SwitchDefaults.colors(

            )
        )
        Spacer(Modifier.width(10.dp))

        contentListChoice()
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        managerText(
            text = "$value ${Resources.getString("unity/percent")}",
            color = color
        )
        managerText(
            text = "$fanValue ${Resources.getString("unity/rpm")}",
            color = color
        )
    }
}