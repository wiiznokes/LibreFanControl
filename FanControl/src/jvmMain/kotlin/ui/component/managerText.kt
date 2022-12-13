package ui.component

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp


@Composable
fun managerText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        maxLines = 1,
        style = style
    )
}


@Composable
fun managerOutlinedTextField(
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    label: String
) {
    val isError = remember { mutableStateOf(false) }
    OutlinedTextField(
        isError = isError.value,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .widthIn(70.dp, 200.dp),
        value = text.value,
        onValueChange = {
            try {
                onValueChange(it)
                isError.value = false
            } catch (e: IllegalArgumentException) {
                text.value = it
                isError.value = true
            }
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
fun managerTextField(
    text: MutableState<String>,
    onValueChange: (String) -> Unit
) {
    val isError = remember { mutableStateOf(false) }
    TextField(
        isError = isError.value,
        modifier = Modifier
            .width(80.dp),
        value = text.value,
        onValueChange = {
            try {
                onValueChange(it)
                isError.value = false
            } catch (e: IllegalArgumentException) {
                text.value = it
                isError.value = true
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true
    )
}


