package ui.screen.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun managerOutlinedTextField(value: String, onValueChange: (it: String) -> Unit, label: String) {
    OutlinedTextField(
        modifier = Modifier,
        value = value,
        onValueChange = {
            onValueChange(it)
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

@Composable
fun managerTextView(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun managerTextHomeScreen(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 40.sp
    )
}


@Composable
fun managerBaseItem() {

}