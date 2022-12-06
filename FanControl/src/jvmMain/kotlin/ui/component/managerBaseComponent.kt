package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import model.hardware.Sensor
import model.item.BehaviorItem
import ui.utils.Resources


@Composable
fun managerText(
    value: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = value,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
        style = MaterialTheme.typography.bodyMedium
    )
}


@Composable
fun managerOutlinedTextField(
    text: MutableState<String>,
    trueName: String,
    onValueChange: ((String) -> Boolean),
    label: String
) {
    val focusRequester = remember { FocusRequester() }

    val hasFocus = remember { mutableStateOf(false) }

    val enabled = remember { mutableStateOf(true) }

    OutlinedTextField(
        enabled = enabled.value,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .widthIn(70.dp, 200.dp)

            .clickable { focusRequester.requestFocus() }
            .focusRequester(focusRequester)
            .onFocusChanged {
                /*
                println("onFocusChanged: " +
                "isFocused = ${it.isFocused}, " +
                "hasFocus = ${it.hasFocus}")
                 */

                if (hasFocus.value && !it.isFocused && !it.hasFocus) {
                    println("onValueChange")
                    enabled.value = false
                    if (onValueChange(text.value)) {

                    } else {
                        text.value = trueName
                    }
                    enabled.value = true
                    hasFocus.value = false
                }

                if (it.isFocused && it.hasFocus) {
                    if (hasFocus.value) {
                        println("onValueChange")
                        enabled.value = false
                        if (onValueChange(text.value)) {

                        } else {
                            text.value = trueName
                        }
                        enabled.value = true
                        hasFocus.value = false
                    } else {
                        hasFocus.value = true
                    }
                }
            }
            .focusable(),
        value = text.value,
        onValueChange = {
            text.value = it
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
    sensorName: String,
    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Sensor) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }



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
                    expanded.value = false
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
    val expanded = remember { mutableStateOf(false) }

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
                    expanded.value = false
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
    expanded: MutableState<Boolean>,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (sensorName != null) {
            managerText(
                value = sensorName,
                modifier = Modifier.align(
                    Alignment.CenterStart
                )
            )
        } else {
            managerText(
                value = "Pas de sensor",
            )
        }

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