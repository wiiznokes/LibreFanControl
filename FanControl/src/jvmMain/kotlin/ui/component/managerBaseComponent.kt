package ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    label: String?,
) {
    OutlinedTextField(
        modifier = Modifier
            .widthIn(1.dp, Dp.Infinity),
        value = value,
        onValueChange = {
            onValueChange?.invoke(it)
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        label = { Text(label!!) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        singleLine = true
    )
}


@Composable
fun managerListChoice(
    sensorName: String,
    onChangeSensorClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        managerTextField(
            value = sensorName,
        )
        IconButton(
            onClick = {
                onChangeSensorClick?.invoke()
            }
        ) {
            Icon(
                painter = Resources.getIcon("expand_more"),
                contentDescription = Resources.getString("changeSensorContentDescription")
            )
        }
    }
}