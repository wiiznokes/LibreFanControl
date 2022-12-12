package ui.component


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.utils.Resources


@Composable
fun baseItem(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
    ) {

        Surface(
            modifier = Modifier
                .padding(18.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSurface
            )
        ) {

            val density = LocalDensity.current

            val finalWidth: MutableState<Dp> = remember { mutableStateOf(0.dp) }



            Column(
                modifier = Modifier
                    .padding(20.dp)

            ) {
                Row(
                    modifier = Modifier
                        .onGloballyPositioned {
                            finalWidth.value = density.run { it.size.width.toDp() }
                        }
                ) {
                    Icon(
                        painter = iconPainter,
                        contentDescription = iconContentDescription
                    )
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
                    )

                    val text = mutableStateOf(name)

                    managerOutlinedTextField(
                        text = text,
                        onValueChange = {
                            onNameChange(it)
                        },
                        label = Resources.getString("label_item_name")
                    )


                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Column(
                    modifier = Modifier
                        .width(finalWidth.value)
                ) {
                    content()
                }
            }
        }




        if (editModeActivated) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.TopEnd),
                onClick = {
                    onEditClick()
                }
            ) {
                Icon(
                    painter = Resources.getIcon("cancel"),
                    contentDescription = Resources.getString("edit_remove_button_content_description"),
                    tint = Color.Red
                )
            }
        }

    }
}





