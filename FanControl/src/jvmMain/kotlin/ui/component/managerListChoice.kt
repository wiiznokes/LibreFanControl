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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import jdk.jfr.Enabled
import ui.utils.Resources

@Composable
fun managerAddItemListChoice(
    name: String
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
                    painter = Resources.getIcon("expand_more"),
                    contentDescription = Resources.getString("ct/choose_sensor")
                )

                managerText(
                    text = name
                )
            }
        }
    }
}


@Composable
fun managerListChoice(
    text: String?,
    enabled: Boolean = true,
    textContent: @Composable (String) -> Unit = {
        managerText(
            text = it,
            enabled = enabled
        )
    },
    baseModifier: Modifier = Modifier
        .fillMaxWidth()
        .widthIn(min = 100.dp, max = 250.dp),
    dropDownModifier: Modifier = Modifier,
    onItemClick: (Long?) -> Unit,
    iconContent: @Composable ((Long, Int) -> Unit)? = null,
    ids: List<Long>,
    names: List<String>,
) {
    val expanded = remember { mutableStateOf(false) }

    managerBaseDropdownMenu(
        text = text ?: Resources.getString("none"),
        textContent = textContent,
        expanded = expanded,
        modifier = baseModifier
    ) {
        managerDropDownContent(
            expanded = expanded,
            text = Resources.getString("none"),
            onItemClick = onItemClick,
            enabled = enabled
        )
        for (i in ids.indices) {
            managerDropDownContent(
                modifier = dropDownModifier,
                expanded = expanded,
                text = names[i],
                onItemClick = onItemClick,
                iconContent = iconContent,
                id = ids[i],
                index = i,
                enabled = enabled
            )
        }
    }
}


@Composable
private fun managerBaseDropdownMenu(
    modifier: Modifier,
    text: String,
    textContent: @Composable (String) -> Unit,
    expanded: MutableState<Boolean>,
    dropDownContent: @Composable ColumnScope.() -> Unit
) {
    Box {
        val iconShouldTrigger = remember { mutableStateOf(true) }

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    IconButton(
                        onClick = {
                            if (!iconShouldTrigger.value) {
                                iconShouldTrigger.value = true
                                return@IconButton
                            }
                            expanded.value = true
                        }
                    ) {
                        val painter = when (expanded.value) {
                            true -> Resources.getIcon("expand_less")
                            false -> Resources.getIcon("expand_more")
                        }

                        Icon(
                            painter = painter,
                            contentDescription = Resources.getString("ct/choose_sensor")
                        )
                    }

                    textContent(text)
                }
            }
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
                iconShouldTrigger.value = false
            },
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
private fun managerDropDownContent(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    text: String,
    onItemClick: (Long?) -> Unit,
    iconContent: @Composable ((Long, Int) -> Unit)? = null,
    id: Long? = null,
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
                        enabled = enabled
                    )
                }
            }
        }
    }
}