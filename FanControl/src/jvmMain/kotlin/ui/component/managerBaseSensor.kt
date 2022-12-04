package ui.component

import Source
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.flow.StateFlow

@Composable
fun baseSensor(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    label: String,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: StateFlow<Boolean>? = null,
    onEditClick: () -> Unit,
    source: Source,

    sensorName: String,
    onChangeSensorClick: (() -> Unit)? = null
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

        managerListChoice(
            sensorName = sensorName,
            onChangeSensorClick = onChangeSensorClick
        )
    }
}