package ui.component

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ui.screen.body.behaviorList.linear.LinearParams
import ui.utils.BlankException
import ui.utils.IndexHaveNameException
import ui.utils.NameIsTakenException


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
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    id: Long,
    text: MutableState<String> = remember(
        id
    ) {
        mutableStateOf(value)
    }
) {

    // if id had change, remember have to update
    // this avoid bug when name of an item
    // get reuse with another item
    val isError = remember(
        id
    ) { mutableStateOf(false) }

    OutlinedTextField(
        isError = isError.value,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .widthIn(70.dp, 200.dp),
        value = text.value,
        onValueChange = {
            text.value = it
            try {
                onValueChange(it)
                isError.value = false
            } catch (e: NameIsTakenException) {
                isError.value = true
            } catch (e: IndexHaveNameException) {
                isError.value = false
            } catch (e: BlankException) {
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
    opposedValue: Int,
    type: LinearParams,
    onValueChange: (Int) -> String
) {
    val isError = try {
        when (type) {
            LinearParams.MIN_TEMP -> text.value.toInt() >= opposedValue
            LinearParams.MAX_TEMP -> text.value.toInt() <= opposedValue
            LinearParams.MIN_FAN_SPEED -> text.value.toInt() >= opposedValue
            LinearParams.MAX_FAN_SPEED -> text.value.toInt() <= opposedValue
        }
    } catch (e: NumberFormatException) {
        true
    }

    TextField(
        modifier = Modifier
            .width(80.dp),
        isError = isError,
        value = text.value,
        onValueChange = {
            try {
                val finalValue = onValueChange(it.toInt())
                text.value = finalValue
            } catch (e: NumberFormatException) {
                text.value = ""
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true
    )
}


