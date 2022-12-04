package ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.utils.Resources


@Composable
fun managerTextField(
    value: String
) {
    TextField(
        modifier = Modifier,
        value = value,
        onValueChange = {},
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary,
        )
    )
}


@Composable
fun managerOutlinedTextField(
    value: String,
    onValueChange: ((String) -> Unit)? = null,
    label: String?,
) {
    OutlinedTextField(
        modifier = Modifier,
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
        maxLines = 1
    )
}


@Composable
fun managerListChoice(
    sensorName: String,
    onChangeSensorClick: (() -> Unit)? = null
) {
    Row {
        TextField(
            modifier = Modifier,
            value = sensorName,
            onValueChange = {},
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
            )
        )
        IconButton(
            onClick = {
                onChangeSensorClick?.invoke()
            }
        ) {
            Icon(
                painter = Resources.getIcon("add"),
                contentDescription = Resources.getString("changeSensorContentDescription")
            )
        }
    }
}