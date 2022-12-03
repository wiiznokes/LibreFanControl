package ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import ui.screen.component.baseItem

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
