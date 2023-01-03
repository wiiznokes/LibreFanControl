package ui.screen.itemsList.behaviorList.linear

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.tokens.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun managerNumberTextField(
    text: MutableState<String>,
    opposedValue: Int,
    type: LinearParams,
    onValueChange: (Int) -> String,
) {
    val colors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
        cursorColor = MaterialTheme.colorScheme.onPrimary
    )

    val interactionSource = remember { MutableInteractionSource() }
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

    @OptIn(ExperimentalMaterial3Api::class)
    (BasicTextField(
        value = text.value,
        modifier = Modifier
            .background(
                color = colors.containerColor(true).value,
                shape = RoundedCornerShape(20.dp)
            )
            .indicatorLine(true, isError, interactionSource, colors)
            .height(50.dp)
            .width(50.dp),
        onValueChange = {
            try {
                val finalValue = onValueChange(it.toInt())
                text.value = finalValue
            } catch (e: NumberFormatException) {
                text.value = ""
            }
        },
        enabled = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = colors.textColor(true).value
        ),
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        interactionSource = interactionSource,
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = text.value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                singleLine = true,
                enabled = true,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(6.dp)
            )
        }
    ))
}