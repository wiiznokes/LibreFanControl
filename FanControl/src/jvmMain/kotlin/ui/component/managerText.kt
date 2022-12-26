package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.tokens.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun managerNumberTextField(
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


@Composable
fun managerNameOutlinedTextField(
    value: String,
    id: Long,
    text: MutableState<String> = remember(
        id
    ) {
        mutableStateOf(value)
    },
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    label: String,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.onPrimary,
        containerColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
        focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
    ),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // if id had change, remember have to update
    // this avoid bug when name of an item
    // get reuse with another item
    val isError = remember(
        id
    ) { mutableStateOf(false) }

    val mergedTextStyle = textStyle.merge(TextStyle(color = colors.textColor(true).value))

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = text.value,
        modifier = modifier
            .padding(top = 8.dp)
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = colors,
                focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
            )
            .background(colors.containerColor(true).value)
            .widthIn(min = 90.dp, max = 100.dp)
            .width(IntrinsicSize.Min)
            .height(40.dp),
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
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError.value).value),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                label = {
                    Text(label)
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
                        colors = colors
                    )
                },
                contentPadding = PaddingValues(0.dp),
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
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colorScheme.onSecondary,
        containerColor = MaterialTheme.colorScheme.secondary,
        focusedBorderColor = MaterialTheme.colorScheme.onSecondary,
        focusedLabelColor = MaterialTheme.colorScheme.onSecondary,
    )
) {
    // if id had change, remember have to update
    // this avoid bug when name of an item
    // get reuse with another item
    val isError = remember(
        id
    ) { mutableStateOf(false) }


    val mergedTextStyle = textStyle.merge(TextStyle(color = colors.textColor(true).value))

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = value,
        modifier = modifier
            .background(
                colors.containerColor(true).value,
                shape = RoundedCornerShape(22.dp), //rounded corners
            )
            .padding(horizontal = 10.dp)
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource = interactionSource,
                colors = colors,
                focusedIndicatorLineThickness = 0.dp,  //to hide the indicator line
                unfocusedIndicatorLineThickness = 0.dp //to hide the indicator line
            )
            .widthIn(200.dp, 250.dp),
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
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError.value).value),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    if (placeholder != null) {
                        Text(
                            text = placeholder,
                            fontSize = 20.sp,
                        )
                    }
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