package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.tokens.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ui.theme.LocalColors
import ui.theme.LocalShapes
import ui.theme.LocalSpaces
import utils.NameException


@Composable
fun managerNameTextField(
    value: String,
    ids: Pair<Long?, Long?>,
    text: MutableState<String> = remember(ids.first, ids.second) {
        mutableStateOf(value)
    },
    onValueChange: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
        .height(30.dp),
    placeholder: String? = null,
    color: Color = LocalColors.current.input,
    onColor: Color = LocalColors.current.onInput,
    shape: Shape = LocalShapes.current.small,
    enabled: Boolean = true
) {

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = onColor,
        containerColor = color,
        focusedBorderColor = onColor,
        focusedLabelColor = onColor,
        unfocusedLabelColor = onColor,
        cursorColor = onColor,
        unfocusedBorderColor = onColor.copy(
            alpha = 0.5f
        )
    )

    val isError = remember(ids.first, ids.second) {
        mutableStateOf(false)
    }

    val interactionSource = remember { MutableInteractionSource() }

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = text.value,
        enabled = enabled,
        modifier = modifier
            .background(
                shape = shape,
                color = colors.containerColor(true).value
            ),
        onValueChange = {
            text.value = it
            try {
                onValueChange?.invoke(it)
                isError.value = false
            } catch (e: NameException) {
                isError.value = true
            }
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = colors.textColor(true).value
        ),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = text.value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    if (placeholder != null) {
                        managerText(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.textColor(true).value
                        )
                    }
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
                        focusedBorderThickness = 2.dp,
                        unfocusedBorderThickness = 2.dp,
                        shape = shape
                    )
                },
                contentPadding = PaddingValues(horizontal = LocalSpaces.current.medium),
                enabled = enabled
            )
        }
    )
}