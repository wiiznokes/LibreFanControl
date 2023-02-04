package ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.theme.LocalColors
import ui.utils.Resources

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun managerExpandItem(
    value: Int,
    color: Color = LocalColors.current.onMainContainer,
    expanded: MutableState<Boolean> = mutableStateOf(true),
    suffix: String = Resources.getString("unity/percent"),
    content: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        managerText(
            text = "$value $suffix",
            color = color
        )

        Icon(
            modifier = Modifier
                .clickable { expanded.value = !expanded.value },
            painter = when (expanded.value) {
                true -> Resources.getIcon("arrow/expand/expand_less24")
                false -> Resources.getIcon("arrow/expand/expand_more24")
            },
            contentDescription = null,
            tint = color
        )
    }


    AnimatedContent(
        targetState = expanded.value,
        transitionSpec = {
            slideInVertically() with slideOutVertically()
        }
    ) {
        if (it) {
            // we need Columns somehow
            Column {
                content?.invoke()
            }
        }

    }
}