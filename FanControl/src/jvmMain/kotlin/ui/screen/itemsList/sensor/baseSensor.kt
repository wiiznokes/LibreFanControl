package ui.screen.itemsList.sensor


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.painter.Painter
import model.hardware.Sensor
import model.item.sensor.SensorItem
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemBody
import ui.theme.LocalColors


@Composable
fun baseSensorBody(
    icon: Painter,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,

    sensorName: String?,
    sensorValue: String,

    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Long?) -> Unit,
    sensorItem: SensorItem
) {
    baseItemBody(
        icon = icon,
        item = sensorItem,
        onNameChange = onNameChange,
        onEditClick = onEditClick
    ) {
        managerListChoice(
            text = sensorName,
            onItemClick = onItemClick,
            ids = sensorList.map { it.id },
            names = sensorList.map { it.name }
        )
        managerText(
            text = sensorValue,
            color = LocalColors.current.onMainContainer,
        )
    }
}