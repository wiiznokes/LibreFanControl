package ui.screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow
import ui.component.managerOutlinedTextField
import ui.component.managerTextView


@Composable
fun baseItem(
    content: @Composable (ColumnScope.() -> Unit),
    editModeActivated: StateFlow<Boolean>,
    onRemove: () -> Unit,
    name: String,
    onNameChange: (it: String) -> Unit
) {


    Surface(
        modifier = Modifier
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Box {

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {

                managerOutlinedTextField(
                    value = name,
                    label = "name",
                    onValueChange = {
                        onNameChange(it)
                    }
                )

                content()

            }

            if (editModeActivated.value) {
                Button(
                    onClick = {
                        onRemove()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Text("remove")
                }
            }
        }
    }
}



@Composable
fun managerBaseSensorBody(
    editModeActivated: StateFlow<Boolean>,
    onRemove: () -> Unit,
    name: String,
    onNameChange: (it: String) -> Unit,
    value: String,
    suffix: String
) {

    baseItem(
        editModeActivated = editModeActivated,
        onRemove = onRemove,
        name = name,
        onNameChange = onNameChange,
        content = {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Row {
                managerTextView(
                    text = "value:"
                )
                managerTextView(
                    text = "$value$suffix"
                )
            }
        }
    )
}
