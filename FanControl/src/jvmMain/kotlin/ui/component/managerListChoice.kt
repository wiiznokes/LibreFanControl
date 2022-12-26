package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                text = name,
                modifier = Modifier.align(
                    Alignment.CenterStart
                )
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
    textContent: @Composable BoxScope.() -> Unit,
    expanded: MutableState<Boolean>,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {

        textContent()


        val iconShouldTrigger = remember { mutableStateOf(true) }
        IconButton(
            modifier = Modifier.align(
                Alignment.CenterEnd
            ),
            onClick = {
                if (!iconShouldTrigger.value) {
                    iconShouldTrigger.value = true
                    return@IconButton
                }
                expanded.value = true
            }
        ) {
            if (expanded.value) {
                Icon(
                    painter = Resources.getIcon("expand_more"),
                    contentDescription = Resources.getString("ct/choose_sensor")
                )
            } else {
                Icon(
                    painter = Resources.getIcon("expand_less"),
                    contentDescription = Resources.getString("ct/choose_sensor")
                )
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