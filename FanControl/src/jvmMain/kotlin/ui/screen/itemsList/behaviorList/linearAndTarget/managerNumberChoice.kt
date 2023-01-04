package ui.screen.itemsList.behaviorList.linearAndTarget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources

@Composable
fun managerNumberChoice(
    text: @Composable () -> Unit,
    prefix: String,
    suffix: String,

    increase: (() -> Unit)? = null,
    decrease: (() -> Unit)? = null,
    color: Color
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
                        .width(90.dp),
                    style = MaterialTheme.typography.bodySmall,
                    text = prefix,
                    color = color
                )
                text()
                Spacer(Modifier.width(5.dp))
                managerText(
                    text = suffix,
                    color = color
                )
            }
        }
        Column {
            IconButton(
                modifier = Modifier.scale(0.8f).requiredSize(20.dp),
                onClick = increase ?: {}
            ) {
                Icon(

                    painter = Resources.getIcon("add"),
                    contentDescription = Resources.getString("ct/increase"),
                    tint = color
                )
            }
            IconButton(
                modifier = Modifier.scale(0.8f).requiredSize(20.dp),
                onClick = decrease ?: {}
            ) {
                Icon(

                    painter = Resources.getIcon("remove"),
                    contentDescription = Resources.getString("ct/decrease"),
                    tint = color
                )
            }
        }
    }
}


fun numberChoiceFinalValue(value: Int): Int =
    if (value < 0)
        0
    else {
        if (value > 100)
            100
        else
            value
    }