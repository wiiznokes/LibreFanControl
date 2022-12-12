package ui.component


import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.hardware.Sensor

@Composable
fun baseSensor(
    onEditClick: () -> Unit,
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,

    sensorName: String,
    sensorValue: String,

    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Sensor?) -> Unit
) {
    baseItem(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        name = name,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
    ) {

        listChoice(
            sensorName = sensorName,
            sensorList = sensorList,
            onItemClick = onItemClick
        )
        managerText(
            text = sensorValue
        )

    }
}