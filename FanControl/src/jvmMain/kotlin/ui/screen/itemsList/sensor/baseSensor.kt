package ui.screen.itemsList.sensor


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.ItemType
import model.hardware.Sensor
import model.item.SensorItem
import ui.component.managerAddItemListChoice
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody


@Composable
fun baseSensorBody(
    onEditClick: () -> Unit,
    iconPainter: Painter,
    iconContentDescription: String,
    onNameChange: (String) -> Unit,

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
            text = sensorValue,
            color = MaterialTheme.colorScheme.onSurface
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
            text = sensorValue,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}