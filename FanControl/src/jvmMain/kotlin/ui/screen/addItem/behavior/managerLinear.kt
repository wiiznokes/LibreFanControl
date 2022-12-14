package ui.screen.addItem.behavior

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.baseItemAddItem
import ui.component.managerText
import ui.utils.Resources

@Composable
fun managerAddLinear(
    onEditClick: () -> Unit,
) {
    baseItemAddItem(
        iconPainter = Resources.getIcon("linear"),
        iconContentDescription = Resources.getString("ct/linear"),
        name = "behavior linear",
        onEditClick = onEditClick
    ) {
        setting(
            value = "20",
            prefix = Resources.getString("linear/min_temp"),
            suffix = Resources.getString("unity/degree"),
        )
        setting(
            value = "70",
            prefix = Resources.getString("linear/max_temp"),
            suffix = Resources.getString("unity/degree"),
        )
        setting(
            value = "50",
            prefix = Resources.getString("linear/min_fan_speed"),
            suffix = Resources.getString("unity/percent"),
        )
        setting(
            value = "100",
            prefix = Resources.getString("linear/max_fan_speed"),
            suffix = Resources.getString("unity/percent"),
        )
    }
}


@Composable
private fun setting(
    value: String,
    prefix: String,
    suffix: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                managerText(
                    modifier = Modifier
                        .width(120.dp),
                    text = prefix
                )
                managerText(
                    text = value
                )
                Spacer(
                    modifier = Modifier
                        .width(5.dp)
                )
                managerText(
                    text = suffix
                )
            }
        }
        Column {
            Icon(
                painter = Resources.getIcon("add"),
                contentDescription = Resources.getString("ct/increase")
            )
            Icon(
                painter = Resources.getIcon("remove"),
                contentDescription = Resources.getString("ct/decrease")
            )
        }
    }
}