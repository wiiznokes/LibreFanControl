package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import model.item.BehaviorItem
import ui.utils.Resources


@Composable
fun managerText(
    value: String
) {
    Text(
        modifier = Modifier,
        text = value,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
        style = MaterialTheme.typography.bodyMedium
    )
}


@Composable
fun managerOutlinedTextField(
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    label: String
) {

    OutlinedTextField(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .widthIn(70.dp, 200.dp),
        value = value,
        onValueChange = {
            onValueChange?.invoke(it)
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        label = { Text(label) }
    )
}


@Composable
fun listChoice(
    sensorName: String?,
    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Sensor) -> Unit
) {
    val expanded = MutableStateFlow(mutableStateOf(false))



    managerListChoice(
        sensorName = sensorName,
        expanded = expanded
    ) {
        sensorList.forEach {
            DropdownMenuItem(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                onClick = {
                    onItemClick(it)
                    expanded.update {
                        it.value = false
                        it
                    }
                }
            ) {
                managerText(
                    value = it.libName
                )
            }
        }
    }
}

@JvmName("listChoice1")
@Composable
fun listChoice(
    sensorName: String?,
    behaviorItemList: SnapshotStateList<BehaviorItem>,
    onItemClick: (String) -> Unit
) {
    val expanded = MutableStateFlow(mutableStateOf(false))

    managerListChoice(
        sensorName = sensorName,
        expanded = expanded
    ) {
        behaviorItemList.forEach {
            DropdownMenuItem(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary),
                onClick = {
                    onItemClick(it.name)
                    expanded.update {
                        it.value = false
                        it
                    }
                }
            ) {
                managerText(
                    value = it.name
                )
            }
        }
    }
}

@Composable
private fun managerListChoice(
    sensorName: String?,
    expanded: MutableStateFlow<MutableState<Boolean>>,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (sensorName != null) {
            managerText(
                value = sensorName,
            )
        } else {
            managerText(
                value = "Pas de sensor",
            )
        }

        val iconShouldTrigger = remember { mutableStateOf(true) }
        IconButton(
            onClick = {
                if(!iconShouldTrigger.value) {
                    iconShouldTrigger.value = true
                    return@IconButton
                }
                expanded.update {
                    it.value = true
                    it
                }
            }
        ) {
            if (expanded.value.value) {
                Icon(
                    painter = Resources.getIcon("expand_more"),
                    contentDescription = Resources.getString("changeSensorContentDescription")
                )
            } else {
                Icon(
                    painter = Resources.getIcon("expand_less"),
                    contentDescription = Resources.getString("changeSensorContentDescription")
                )
            }
        }

        DropdownMenu(
            expanded = expanded.value.value,
            onDismissRequest = {
                expanded.update {
                    it.value = false
                    it
                }
                iconShouldTrigger.value = false
            }
        ) {
            content()
        }
    }
}