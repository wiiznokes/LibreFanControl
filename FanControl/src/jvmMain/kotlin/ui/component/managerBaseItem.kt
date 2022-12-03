package ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow

@Composable
private fun baseItem(
    editModeActivated: StateFlow<Boolean>,
    onEditChange: () -> Unit,
    content: @Composable (ColumnScope.() -> Unit)
) {

    Surface(
        modifier = Modifier
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Box {

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                content()

            }
            if (editModeActivated.value) {
                Button(
                    onClick = {
                        onEditChange()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Text("remove")
                }
            }
        }

    }
}

@Composable
private fun baseSensor(
    editModeActivated: StateFlow<Boolean>,
    onEditChange: () -> Unit,
    value: String,
    suffix: String,
    content: @Composable ColumnScope.() -> Unit
) {

    baseItem(
        editModeActivated = editModeActivated,
        onEditChange = onEditChange,
        content = {
            content()
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            Row {
                Text(
                    text = value,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = suffix,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@Composable
fun baseSensorBody(
    editModeActivated: StateFlow<Boolean>,
    onEditChange: () -> Unit,
    value: String,
    suffix: String,
    name: String,
    label: String,
    onNameChange: (String) -> Unit
) {

    baseSensor(
        editModeActivated = editModeActivated,
        onEditChange = onEditChange,
        value = value,
        suffix = suffix
    ) {
        OutlinedTextField(
            modifier = Modifier,
            value = name,
            onValueChange = {
                onNameChange(it)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            label = { Text(label) },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            maxLines = 1
        )
    }
}



@Composable
fun baseAddSensor(
    editModeActivated: StateFlow<Boolean>,
    onEditChange: () -> Unit,
    value: String,
    suffix: String,
    name: String,
) {

    baseSensor(
        editModeActivated = editModeActivated,
        onEditChange = onEditChange,
        value = value,
        suffix = suffix
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun baseBodyBehavior(
    editModeActivated: StateFlow<Boolean>,
    onEditChange: () -> Unit,
    value: String,
    suffix: String,
    name: String,
) {

    baseSensor(
        editModeActivated = editModeActivated,
        onEditChange = onEditChange,
        value = value,
        suffix = suffix
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


