package ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.tokens.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
fun managerNameOutlinedTextField(
    value: String,
    ids: Pair<Long?, Long?>,
    text: MutableState<String> = remember(ids.first, ids.second) {
        mutableStateOf(value)
    },
    onValueChange: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
        .widthIn(min = 90.dp, max = 180.dp)
        .width(IntrinsicSize.Min)
        .height(50.dp),
    label: String? = null,
    color: Color = MaterialTheme.colorScheme.primary,
    onColor: Color = MaterialTheme.colorScheme.onPrimary,
    cornerShape: Dp = 2.dp,
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
                shape = RoundedCornerShape(cornerShape),
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
                    if (label != null) {
                        managerText(
                            text = label,
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
                        shape = RoundedCornerShape(cornerShape)
                    )
                },
                contentPadding = PaddingValues(horizontal = 10.dp),
                enabled = enabled
            )
        }
    )
}