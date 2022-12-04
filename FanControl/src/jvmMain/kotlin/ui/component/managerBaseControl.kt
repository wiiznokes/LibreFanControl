package ui.component

import Source
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.flow.StateFlow


@Composable
fun baseControl(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    label: String,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: StateFlow<Boolean>? = null,
    onEditClick: () -> Unit,
    source: Source,

    behaviorName: String,
    onChangeBehaviorClick: (() -> Unit)? = null,
    isActive: Boolean = false,
    onSwitchClick: ((Boolean) -> Unit)? = null,
    value: String = "50 %",
    fanValue: String = "2000 RPM"
) {
    baseItem(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        name = name,
        label = label,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        source = source,
    ) {
        Row {

            Switch(
                checked = isActive,
                onCheckedChange = {
                    onSwitchClick?.invoke(it)
                }
            )

            managerListChoice(
                sensorName = behaviorName,
                onChangeSensorClick = onChangeBehaviorClick
            )
        }
        Row {
            managerTextField(value)
            managerTextField(fanValue)
        }
    }
}