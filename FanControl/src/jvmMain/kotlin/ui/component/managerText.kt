package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import ui.screen.body.behaviorList.linear.LinearParams
import utils.NameException


@Composable
fun managerText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
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
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Number
    ),
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
        cursorColor = Color.Black
    )
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

    val mergedTextStyle = textStyle.merge(TextStyle(color = colors.textColor(true).value))

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = text.value,
        modifier = modifier
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
        readOnly = false,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            // places leading icon, text field with label and placeholder, trailing icon
            TextFieldDefaults.TextFieldDecorationBox(
                value = text.value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = true,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(6.dp)
            )
        }
    )
}


@Composable
fun managerNameOutlinedTextField(
    ids: Pair<Long, Long?>,
    text: MutableState<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    label: String,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
        cursorColor = Color.Black
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // if id had change, remember have to update
    // this avoid bug when name of an item
    // get reuse with another item
    val isError = remember(
        ids.first, ids.second
    ) { mutableStateOf(false) }

    val mergedTextStyle = textStyle.merge(TextStyle(color = colors.textColor(true).value))

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = text.value,
        modifier = modifier
            .padding(top = 8.dp)
            .indicatorLine(
                enabled = true,
                isError = isError.value,
                interactionSource = interactionSource,
                colors = colors,
                focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
            )
            .background(colors.containerColor(true).value),
        onValueChange = {
            text.value = it
            try {
                onValueChange(it)
                isError.value = false
            } catch (e: NameException) {
                isError.value = true
            }
        },
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError.value).value),
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
                        style = MaterialTheme.typography.labelSmall
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
                    )
                },
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                enabled = true
            )
        }
    )
}


@Composable
fun managerConfigNameRoundedTextField(
    value: String,
    id: Long,
    text: MutableState<String> = remember(
        id
    ) {
        mutableStateOf(value)
    },
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colorScheme.onSecondary,
        containerColor = MaterialTheme.colorScheme.secondary,
        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
        errorIndicatorColor = MaterialTheme.colorScheme.onError,
        errorCursorColor = MaterialTheme.colorScheme.onError
    )
) {
    // if id had change, remember have to update
    // this avoid bug when name of an item
    // get reuse with another item
    val isError = remember(
        id
    ) { mutableStateOf(false) }


    val mergedTextStyle = textStyle.merge(TextStyle(color = colors.textColor(true).value))

    val errorColor = if (isError.value)
        MaterialTheme.colorScheme.onError
    else
        colors.containerColor(true).value

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = value,
        modifier = modifier
            .background(
                color = colors.containerColor(true).value,
                shape = RoundedCornerShape(22.dp), //rounded corners
            )
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(22.dp),
                color = errorColor
            )
            .padding(horizontal = 10.dp)
            .indicatorLine(
                enabled = true,
                isError = isError.value,
                interactionSource = interactionSource,
                colors = colors,
                focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
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
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError.value).value),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    managerText(
                        text = placeholder,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                singleLine = true,
                enabled = true,
                isError = isError.value,
                interactionSource = interactionSource,
                colors = colors,
                contentPadding = PaddingValues(0.dp)
            )
        }
    )
}