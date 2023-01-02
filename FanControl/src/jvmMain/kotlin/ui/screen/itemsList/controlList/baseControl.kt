package ui.screen.itemsList.controlList


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources


@Composable
fun baseControl(
    isAuto: Boolean,
    switchEnabled: Boolean,
    onSwitchClick: (Boolean) -> Unit,
    value: Int,
    fanValue: String,
    contentListChoice: @Composable () -> Unit
) {

    Row {

        Switch(
            enabled = switchEnabled,
            checked = !isAuto,
            onCheckedChange = {
                onSwitchClick(it)
            }
        )
        Spacer(
            modifier = Modifier
                .width(10.dp)
        )

        contentListChoice()


    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        managerText("$value ${Resources.getString("unity/percent")}")
        managerText(fanValue)
    }

}