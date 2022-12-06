package ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.hardware.Sensor
import model.item.BehaviorItem
import ui.utils.Resources


@Composable
fun managerTextField(
    value: String
) {
    Text(
        modifier = Modifier,
        text = value,
        color = androidx.compose.material.MaterialTheme.colors.onPrimary,
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
    managerListChoice(
        sensorName = sensorName,
    ) {
        sensorList.forEach {
            DropdownMenuItem(
                onClick = {
                    onItemClick(it)
                }
            ) {
                Text(
                    text = it.libName
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
    managerListChoice(
        sensorName = sensorName,
    ) {
        behaviorItemList.forEach {
            DropdownMenuItem(
                onClick = {
                    onItemClick(it.name)
                }
            ) {
                Text(
                    text = it.name
                )
            }
        }
    }
}

@Composable
private fun managerListChoice(
    sensorName: String?,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (sensorName != null) {
            managerTextField(
                value = sensorName,
            )
        } else {
            managerTextField(
                value = "Pas de sensor",
            )
        }

        var expanded by remember { mutableStateOf(false) }

        IconButton(
            onClick = {
                expanded = !expanded
            }
        ) {
            if (expanded) {
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
            expanded = expanded,
            onDismissRequest = {
                expanded = !expanded
            }
        ) {
            content()
        }
    }
}