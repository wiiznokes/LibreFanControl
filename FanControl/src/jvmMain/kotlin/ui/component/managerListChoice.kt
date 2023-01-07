package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import ui.utils.Resources

@Composable
fun managerAddItemListChoice(
    name: String,
    painterType: PainterType = PainterType.CHOOSE
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                Icon(
                    painter = when (painterType) {
                        PainterType.ADD -> Resources.getIcon("sign/plus/add24")
                        PainterType.CHOOSE -> Resources.getIcon("arrow/dropDown/arrow_drop_down40")
                    },
                    contentDescription = Resources.getString("ct/choose"),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                managerText(
                    text = name,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Composable
fun <T> managerListChoice(
    text: String?,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textContent: @Composable (String) -> Unit = {
        managerText(
            text = it,
            color = color,
            enabled = enabled
        )
    },
    painterType: PainterType = PainterType.CHOOSE,
    size: Int = 40,
    baseModifier: Modifier = Modifier
        .fillMaxWidth()
        .widthIn(min = 100.dp, max = 250.dp),
    itemModifier: Modifier = Modifier,
    onItemClick: (T?) -> Unit,
    iconContent: @Composable ((T, Int) -> Unit)? = null,
    ids: List<T>,
    names: List<String>,
    addNoneItem: Boolean = true
) {
    val expanded = remember { mutableStateOf(false) }

    managerBaseDropdownMenu(
        text = text ?: Resources.getString("none"),
        textContent = textContent,
        expanded = expanded,
        painterType = painterType,
        size = size,
        modifier = baseModifier,
        expandIconColor = color
    ) {
        if (addNoneItem) {
            managerDropDownContent(
                modifier = itemModifier,
                expanded = expanded,
                text = Resources.getString("none"),
                onItemClick = onItemClick,
                enabled = enabled
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
    expandIconColor: Color,
    painterType: PainterType,
    size: Int,
    dropDownContent: @Composable ColumnScope.() -> Unit
) {
    Box {


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
                            tint = expandIconColor
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
                    color = MaterialTheme.colorScheme.primary
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
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
    enabled: Boolean
) {
    DropdownMenuItem(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary),
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
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}