package ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun managerListChoice(
    sensorName: String,
    onChangeSensorClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
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