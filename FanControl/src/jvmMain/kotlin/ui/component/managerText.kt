package ui.component

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ui.screen.itemsList.behaviorList.linear.LinearParams
import utils.NameException


@Composable
fun managerText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color,
    enabled: Boolean = true
) {
    Text(
        modifier = modifier,
        text = text,
        color = if (enabled)
            color
        else color.copy(
            alpha = 0.8f
        ),
        maxLines = 1,
        style = style,
        overflow = TextOverflow.Ellipsis
    )
}

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
    BasicTextField(
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
    )
}

private val cornerShape = 2.dp

@Composable
fun managerNameOutlinedTextField(
    ids: Pair<Long, Long?>,
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
        cursorColor = MaterialTheme.colorScheme.onPrimary,
        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(
            alpha = 0.8f
        )
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // if id had change, remember have to update
    // this avoid bug when name of an item
    // get reuse with another item
    val isError = remember(
        ids.first, ids.second
    ) { mutableStateOf(false) }

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = text.value,
        modifier = modifier
            .padding(top = 8.dp)
            .background(
                shape = RoundedCornerShape(cornerShape),
                color = colors.containerColor(true).value
            ),
        onValueChange = {
            text.value = it
            try {
                onValueChange(it)
                isError.value = false
            } catch (e: NameException) {
                isError.value = true
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = colors.textColor(true).value
        ),
        singleLine = true,
        interactionSource = interactionSource,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = text.value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                label = {
                    managerText(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                singleLine = true,
                isError = isError.value,
                interactionSource = interactionSource,
                colors = colors,
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled = true,
                        isError = isError.value,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = RoundedCornerShape(cornerShape)
                    )
                },
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                enabled = true
            )
        }
    )
}