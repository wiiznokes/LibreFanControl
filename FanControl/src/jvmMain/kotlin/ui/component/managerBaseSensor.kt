package ui.component


import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.hardware.Sensor
import model.item.SensorItem


@Composable
fun baseSensorBody(
    onEditClick: () -> Unit,
    iconPainter: Painter,
    iconContentDescription: String,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,

    sensorName: String,
    sensorValue: String,

    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Sensor?) -> Unit,
    sensor: SensorItem
) {
    baseItemBody(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        item = sensor
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

@Composable
fun baseSensorAddItem(
    iconPainter: Painter,
    iconContentDescription: String,
    name: String,
    onEditClick: () -> Unit,
    sensorName: String,
    sensorValue: String,
) {
    baseItemAddItem(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        name = name,
        onEditClick = onEditClick,
    ) {
        listChoice(
            name = sensorName
        )
        managerText(
            text = sensorValue
        )
    }
}