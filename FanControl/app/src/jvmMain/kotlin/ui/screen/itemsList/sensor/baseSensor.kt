package ui.screen.itemsList.sensor


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import model.hardware.Sensor
import model.item.sensor.SensorI
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemBody
import ui.theme.LocalColors
import ui.theme.LocalSpaces


@Composable
fun baseSensorBody(
    icon: Painter,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,

    sensorName: String?,
    sensorValue: String,

    sensorList: SnapshotStateList<Sensor>,
    onItemClick: (Long?) -> Unit,
    sensorItem: SensorI
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

        Spacer(Modifier.height(LocalSpaces.current.medium))

        managerText(
            text = sensorValue,
            color = LocalColors.current.onMainContainer,
        )
    }
}