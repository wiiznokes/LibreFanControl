package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ui.theme.LocalColors
import ui.utils.Resources


data class ListChoiceColors(
    val base: Color,
    val onBase: Color,
    val container: Color,
    val onContainer: Color,
)


@Immutable
object ListChoiceDefault{

    @Composable
    fun listChoiceColors(
        base: Color = LocalColors.current.mainSurface,
        onBase: Color = LocalColors.current.onMainSurface,
        container: Color = LocalColors.current.inputVariant,
        onContainer: Color = LocalColors.current.onInputVariant,

    ): ListChoiceColors =  ListChoiceColors(
        base = base,
        onBase = onBase,
        container = container,
        onContainer = onContainer
    )

}




@Composable
fun <T> managerListChoice(
    text: String? = null,
    enabled: Boolean = true,
    colors: ListChoiceColors = ListChoiceDefault.listChoiceColors(),
    textContent: @Composable (String) -> Unit = {
        managerText(
            text = it,
            color = colors.onBase,
            enabled = enabled
        )
    },
    painterType: PainterType = PainterType.CHOOSE,
    size: Int = 24,
    baseModifier: Modifier = Modifier
        .fillMaxWidth(),
    itemModifier: Modifier = Modifier,
    onItemClick: (T?) -> Unit,
    iconContent: @Composable ((T, Int) -> Unit)? = null,
    ids: List<T>,
    names: List<String>,
    addNoneItem: Boolean = true
) {
    val expanded = remember { mutableStateOf(false) }

    managerBaseDropdownMenu(
        text = text ?: Resources.getString("common/none"),
        textContent = textContent,
        expanded = expanded,
        painterType = painterType,
        size = size,
        modifier = baseModifier,
        colors = colors
    ) {
        if (addNoneItem) {
            managerDropDownContent(
                modifier = itemModifier,
                expanded = expanded,
                text = Resources.getString("common/none"),
                onItemClick = onItemClick,
                enabled = enabled,
                colors = colors
            )
        }

        for (i in ids.indices) {
            managerDropDownContent(
                modifier = itemModifier,
                expanded = expanded,
                text = names[i],
                onItemClick = onItemClick,
                iconContent = iconContent,
                id = ids[i],
                index = i,
                enabled = enabled,
                colors = colors
            )
        }
    }
}


enum class PainterType {
    ADD,
    CHOOSE
}

@Composable
private fun managerBaseDropdownMenu(
    modifier: Modifier,
    text: String,
    textContent: @Composable (String) -> Unit,
    expanded: MutableState<Boolean>,
    colors: ListChoiceColors,
    painterType: PainterType,
    size: Int,
    dropDownContent: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = colors.base,
        shape = MaterialTheme.shapes.small
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    IconButton(
                        modifier = Modifier,
                        onClick = { expanded.value = true }
                    ) {
                        val painter = when (painterType) {
                            PainterType.ADD -> {
                                when (expanded.value) {
                                    true -> Resources.getIcon("select/close/close24")
                                    false -> Resources.getIcon("sign/plus/add24")
                                }
                            }

                            PainterType.CHOOSE -> {
                                when (expanded.value) {
                                    true -> Resources.getIcon("arrow/dropDown/arrow_drop_up$size")
                                    false -> Resources.getIcon("arrow/dropDown/arrow_drop_down$size")
                                }
                            }
                        }

                        Icon(
                            modifier = Modifier,
                            painter = painter,
                            contentDescription = Resources.getString("ct/choose"),
                            tint = colors.onBase
                        )
                    }

                    textContent(text)
                }
            }
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .background(
                    color = colors.container
                )
                .border(
                    width = 2.dp,
                    color = colors.onContainer
                )
        ) {
            dropDownContent()
        }
    }
}


@Composable
private fun <T> managerDropDownContent(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    text: String,
    onItemClick: (T?) -> Unit,
    iconContent: @Composable ((T, Int) -> Unit)? = null,
    id: T? = null,
    index: Int? = null,
    enabled: Boolean,
    colors: ListChoiceColors,
) {
    DropdownMenuItem(
        modifier = Modifier
            .background(colors.container),
        onClick = {
            if (enabled) {
                onItemClick(id)
                expanded.value = false
            }
        }
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    if (iconContent != null) {
                        iconContent(id!!, index!!)
                    }

                    managerText(
                        text = text,
                        enabled = enabled,
                        color = colors.onContainer
                    )
                }
            }
        }
    }
}