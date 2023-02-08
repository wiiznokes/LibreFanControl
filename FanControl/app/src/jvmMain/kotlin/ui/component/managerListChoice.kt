package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources


data class ListChoiceColors(
    val base: Color,
    val onBase: Color,
    val container: Color,
    val onContainer: Color,
)


@Immutable
object ListChoiceDefault {

    @Composable
    fun listChoiceColors(
        base: Color = LocalColors.current.inputVariant,
        onBase: Color = LocalColors.current.onInputVariant,
        container: Color = LocalColors.current.input,
        onContainer: Color = LocalColors.current.onInput,

        ): ListChoiceColors = ListChoiceColors(
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
            modifier = Modifier
                .padding(start = LocalSpaces.current.small),
            text = it,
            color = colors.onBase,
            enabled = enabled
        )
    },
    painterType: PainterType = PainterType.CHOOSE,
    size: Int = 24,
    baseModifier: Modifier = Modifier
        .fillMaxWidth(),
    itemModifier: Modifier = Modifier
        .size(width = 130.dp, height = 30.dp),
    onItemClick: (T?) -> Unit,
    iconContent: @Composable ((T, Int) -> Unit)? = null,
    ids: List<T>,
    names: List<String>,
    addNoneItem: Boolean = true,
    contentNameClickable: Boolean = true,
) {
    val expanded = remember { mutableStateOf(false) }

    managerBaseDropdownMenu(
        text = text ?: Resources.getString("common/none"),
        textContent = textContent,
        expanded = expanded,
        painterType = painterType,
        size = size,
        modifier = baseModifier,
        colors = colors,
        contentNameClickable = contentNameClickable
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
    contentNameClickable: Boolean = true,
    dropDownContent: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        color = colors.base,
        shape = MaterialTheme.shapes.small
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = if (contentNameClickable)
                    modifier
                        .clickable { expanded.value = !expanded.value }
                else modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                    val painter = when (painterType) {
                        PainterType.ADD -> {
                            when (expanded.value) {
                                true -> Resources.getIcon("select/close/close$size")
                                false -> Resources.getIcon("sign/plus/add$size")
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
                        modifier = Modifier
                            .clickable { expanded.value = !expanded.value },
                        painter = painter,
                        contentDescription = null,
                        tint = colors.onBase
                    )

                    textContent(text)
                }
            }
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = !expanded.value },
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
        modifier = modifier
            .background(colors.container),
        onClick = {
            if (enabled) {
                onItemClick(id)
                expanded.value = false
            }
        },
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            managerText(
                modifier = Modifier
                    .padding(start = LocalSpaces.current.small),
                text = text,
                enabled = enabled,
                color = colors.onContainer
            )

            if (iconContent != null)
                iconContent(id!!, index!!)

        }

    }
}