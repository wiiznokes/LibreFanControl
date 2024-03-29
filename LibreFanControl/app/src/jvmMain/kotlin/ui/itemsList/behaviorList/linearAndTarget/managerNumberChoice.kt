package ui.screen.itemsList.behaviorList.linearAndTarget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import ui.component.managerText
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import utils.Resources

@Composable
fun managerNumberChoice(
    text: @Composable () -> Unit,
    prefix: String,
    suffix: String,

    increase: () -> Unit,
    decrease: () -> Unit,
    color: Color = LocalColors.current.onMainContainer,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    managerText(
                        text = ":",
                        color = color
                    )
                    Spacer(Modifier.width(LocalSpaces.current.small))

                    text()
                    managerText(
                        text = suffix,
                        color = color
                    )

                    Column {
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = increase),
                            painter = Resources.getIcon("sign/plus/add20"),
                            contentDescription = Resources.getString("ct/increase"),
                            tint = color
                        )
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = decrease),
                            painter = Resources.getIcon("sign/minus/remove20"),
                            contentDescription = Resources.getString("ct/decrease"),
                            tint = color
                        )
                    }
                }

                managerText(
                    style = MaterialTheme.typography.bodySmall,
                    text = prefix,
                    color = color
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