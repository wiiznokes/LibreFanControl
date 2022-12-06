package ui.component

import Source
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.hardware.Sensor

@Composable
fun baseSensor(
    onEditClick: () -> Unit,
    source: Source,
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    label: String? = null,
    onNameChange: (String) -> Boolean,
    editModeActivated: Boolean? = null,

    sensorName: String?,
    sensorValue: String,

    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Sensor) -> Unit
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

        listChoice(
            sensorName = sensorName,
            sensorList = sensorList,
            onItemClick = onItemClick
        )
        managerText(
            value = sensorValue
        )

    }
}