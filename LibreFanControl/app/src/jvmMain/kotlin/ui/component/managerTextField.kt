package ui.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import model.item.NameException
import ui.screen.itemsList.behaviorList.linearAndTarget.LinAndTarParams
import ui.screen.itemsList.behaviorList.linearAndTarget.isError
import ui.theme.LocalColors
import ui.theme.LocalShapes
import ui.theme.LocalSpaces


@Composable
fun managerNumberTextField(
    text: MutableState<String>,
    onValueChange: (Int) -> String,
    opposedValue: Int,
    type: LinAndTarParams,
) {
    val isError = isError(type, text.value, opposedValue)

    managerTextField(
        value = text.value,
        ids = Pair(null, null),
        text = text,
        onValueChange = {
            try {
                val finalValue = onValueChange(it.toInt())
                text.value = finalValue
            } catch (e: NumberFormatException) {
                text.value = ""
            }
        },
        modifier = Modifier
            .size(width = 47.dp, height = 30.dp),
        color = LocalColors.current.input,
        onColor = LocalColors.current.onInput,
        shape = LocalShapes.current.small,
        isError = isError
    )

}


@Composable
fun managerNameTextField(
    text: MutableState<String>,
    ids: Pair<String?, String?>,
    onValueChange: ((String) -> Unit)? = null,
    modifier: Modifier = Modifier
        .height(30.dp)
        .fillMaxWidth(),
    placeholder: String? = null,
    color: Color = LocalColors.current.input,
    onColor: Color = LocalColors.current.onInput,
    shape: Shape = LocalShapes.current.small,
    enabled: Boolean = true,
) {

    val isError = remember(ids.first, ids.second) {
        mutableStateOf(false)
    }

    managerTextField(
        value = text.value,
        ids = ids,
        text = text,
        onValueChange = {
            text.value = it
            try {
                onValueChange?.invoke(it)
                isError.value = false
            } catch (e: NameException) {
                isError.value = true
            }
        },
        modifier = modifier,
        placeholder = placeholder,
        color = color,
        onColor = onColor,
        shape = shape,
        enabled = enabled,
        isError = isError.value
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun managerTextField(
    value: String,
    ids: Pair<String?, String?>,
    text: MutableState<String> = remember(ids.first, ids.second) {
        mutableStateOf(value)
    },
    onValueChange: ((String) -> Unit)? = null,
    modifier: Modifier,
    placeholder: String? = null,
    color: Color = LocalColors.current.input,
    onColor: Color = LocalColors.current.onInput,
    shape: Shape = LocalShapes.current.small,
    enabled: Boolean = true,
    isError: Boolean,
) {


    val interactionSource = remember { MutableInteractionSource() }

    @OptIn(ExperimentalMaterial3Api::class)
    BasicTextField(
        value = text.value,
        enabled = enabled,
        modifier = modifier,
        onValueChange = { onValueChange?.invoke(it) },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = onColor
        ),
        singleLine = true,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.TextFieldDecorationBox(
                value = text.value,
                visualTransformation = VisualTransformation.None,
                innerTextField = innerTextField,
                placeholder = {
                    if (placeholder != null) {
                        managerText(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium,
                            color = onColor
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = color,
                    errorIndicatorColor = LocalColors.current.error
                ),
                singleLine = true,
                isError = isError,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = LocalSpaces.current.medium),
                enabled = enabled,
                shape = shape
            )
        }
    )
}