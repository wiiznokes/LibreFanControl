package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            .wrapContentSize(
                unbounded = true
            ),
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
        modifier = Modifier
            .fillMaxWidth(),
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