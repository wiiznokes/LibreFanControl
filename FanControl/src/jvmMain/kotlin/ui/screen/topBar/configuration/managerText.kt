package ui.screen.topBar.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.material3.tokens.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.utils.Resources
import utils.NameException

private val cornerShape = 22.dp


@Composable
fun managerConfigNameRoundedTextField(
    value: String,
    enabled: Boolean,
    id: Long? = null,
    text: MutableState<String> = remember(id) {
        mutableStateOf(value)
    },
    onValueChange: ((String) -> Unit)? = null
) {
    val colors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colorScheme.onTertiary,
        containerColor = MaterialTheme.colorScheme.tertiary,
        focusedLabelColor = MaterialTheme.colorScheme.onTertiary,
        errorIndicatorColor = MaterialTheme.colorScheme.error,
        errorCursorColor = MaterialTheme.colorScheme.error
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isError = remember(id) { mutableStateOf(false) }

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = value,
        modifier = Modifier
            .widthIn(200.dp, 250.dp)
            .height(35.dp)
            .background(
                shape = RoundedCornerShape(cornerShape),
                color = colors.containerColor(true).value
            )
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(cornerShape),
                color = if (isError.value)
                    MaterialTheme.colorScheme.error
                else
                    colors.textColor(true).value
            )
            .padding(horizontal = 10.dp),
        onValueChange = {
            text.value = it
            try {
                onValueChange?.invoke(it)
                isError.value = false
            } catch (e: NameException) {
                isError.value = true
            }
        },
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = colors.textColor(true).value
        ),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    managerText(
                        text = Resources.getString("label/conf_name"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.textColor(true).value
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