package ui.component

import Source
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.StateFlow

@Composable
fun baseSensor(
    onEditClick: () -> Unit,
    source: Source,
    imageVector: ImageVector,
    iconContentDescription: String,
    name: String,
    label: String? = null,
    onNameChange: ((String) -> Unit)? = null,
    editModeActivated: StateFlow<Boolean>? = null,

    sensorName: String,
    onChangeSensorClick: (() -> Unit)? = null
) {
    baseItem(
        imageVector = imageVector,
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