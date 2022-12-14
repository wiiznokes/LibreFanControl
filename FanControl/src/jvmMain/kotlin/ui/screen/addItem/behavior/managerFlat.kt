package ui.screen.addItem.behavior

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.baseItemAddItem
import ui.component.managerText
import ui.utils.Resources

@Composable
fun managerAddFlat(
    onEditClick: () -> Unit,
) {

    baseItemAddItem(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = Resources.getString("ct/flat"),
        name = Resources.getString("add_item/flat_name"),
        onEditClick = onEditClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            managerText(Resources.getString("fan_speed"))

            Box {
                Row {

                    Icon(
                        painter = Resources.getIcon("remove"),
                        contentDescription = Resources.getString("ct/decrease")
                    )

                    Icon(
                        painter = Resources.getIcon("add"),
                        contentDescription = Resources.getString("ct/increase")
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
        ) {

            Slider(
                value = 0.5f,
                steps = 100,
                enabled = false,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(0.75f)
            )
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            managerText(
                text = "50 ${Resources.getString("unity/percent")}"
            )
        }
    }
}