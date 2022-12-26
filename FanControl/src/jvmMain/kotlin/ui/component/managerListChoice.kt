package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import model.hardware.Sensor
import model.item.behavior.BehaviorItem
import ui.utils.Resources


@Composable
fun listChoice(
    name: String
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        managerText(
            text = name,
            modifier = Modifier.align(
                Alignment.CenterStart
            )
        )

        Icon(
            modifier = Modifier.align(
                Alignment.CenterEnd
            ),
            painter = Resources.getIcon("expand_more"),
            contentDescription = Resources.getString("ct/choose_sensor")
        )
    }
}

@Composable
fun listChoice(
    sensorName: String,
    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Sensor?) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }



    managerListChoice(
        name = sensorName,
        expanded = expanded
    ) {
        DropdownMenuItem(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary),
            onClick = {
                onItemClick(null)
                expanded.value = false
            }
        ) {
            managerText(
                text = Resources.getString("none")
            )
        }

        sensorList.forEach {
            DropdownMenuItem(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                onClick = {
                    onItemClick(it)
                    expanded.value = false
                }
            ) {
                managerText(
                    text = it.libId
                )
            }
        }
    }
}

@JvmName("listChoice1")
@Composable
fun listChoice(
    behaviorId: Long?,
    behaviorItemList: SnapshotStateList<BehaviorItem>,
    onItemClick: (Long?) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    managerListChoice(
        name = behaviorItemList.find {
            it.itemId == behaviorId
        }.let {
            it?.name ?: Resources.getString("none")
        },
        expanded = expanded
    ) {
        DropdownMenuItem(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary),
            onClick = {
                onItemClick(null)
                expanded.value = false
            }
        ) {
            managerText(
                text = Resources.getString("none")
            )
        }

        behaviorItemList.forEach {
            DropdownMenuItem(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                onClick = {
                    onItemClick(it.itemId)
                    expanded.value = false
                }
            ) {
                managerText(
                    text = it.name
                )
            }
        }
    }
}

@Composable
private fun managerListChoice(
    name: String,
    expanded: MutableState<Boolean>,
    content: @Composable ColumnScope.() -> Unit
) {
    managerListChoice(
        modifier = Modifier
            .fillMaxWidth(),
        textContent = {
            managerText(
                text = name
            )
        },
        expanded = expanded
    ) {
        content()
    }
}

@Composable
fun managerListChoice(
    modifier: Modifier = Modifier,
    textContent: @Composable () -> Unit,
    expanded: MutableState<Boolean>,
    content: @Composable ColumnScope.() -> Unit
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

                    textContent()
                }
            }
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
                iconShouldTrigger.value = false
            }
        ) {
            content()
        }
    }
}