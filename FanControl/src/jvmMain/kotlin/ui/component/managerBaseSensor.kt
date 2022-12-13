package ui.component


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.hardware.Sensor


@Composable
fun baseSensorBody(
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
    baseItemBody(
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
        managerListChoice(
            name = sensorName,
            expanded = mutableStateOf(false),
            content = {}
        )
        managerText(
            text = sensorValue
        )
    }
}