package ui.component


import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.ItemType
import model.hardware.Sensor
import model.item.SensorItem


@Composable
fun baseSensorBody(
    onEditClick: () -> Unit,
    iconPainter: Painter,
    iconContentDescription: String,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,

    sensorName: String?,
    sensorValue: String,

    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Long?) -> Unit,
    sensorItem: SensorItem
) {
    baseItemBody(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        item = sensorItem
    ) {
        managerListChoice(
            text = sensorName,
            onItemClick = onItemClick,
            ids = sensorList.map { it.id },
            names = sensorList.map { it.libName }
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
    type: ItemType
) {
    baseItemAddItem(
        iconPainter = iconPainter,
        iconContentDescription = iconContentDescription,
        name = name,
        onEditClick = onEditClick,
        type = type
    ) {
        managerAddItemListChoice(
            name = sensorName
        )
        managerText(
            text = sensorValue
        )
    }
}