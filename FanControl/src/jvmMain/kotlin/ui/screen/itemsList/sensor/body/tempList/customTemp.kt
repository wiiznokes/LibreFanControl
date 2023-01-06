package ui.screen.itemsList.sensor.body.tempList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.hardware.Sensor
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType
import model.item.sensor.SensorItem
import ui.component.PainterType
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources


/**
 * 1
 * custom type choice (list choice),
 * 2
 * add sensor (list choice static name, icons: + x)
 * 3
 * list sensor in the list with an option to remove
 *
 *
 * calculate value + expand (position -> if (expand) after 3 else between 1 et 2
 */


@Composable
fun baseCustomTempBody(
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    sensorItem: SensorItem,
    onCustomTypeChange: (CustomTempType) -> Unit,
    onAddTempSensor: (Long) -> Unit,
    sensorList: SnapshotStateList<Sensor>,
    onRemoveTemp: (Long) -> Unit
) {
    val customTemp = sensorItem.extension as CustomTemp

    baseItemBody(
        iconPainter = Resources.getIcon("thermostat"),
        iconContentDescription = Resources.getString("ct/custom_temp"),
        onNameChange = onNameChange,
        onEditClick = onEditClick,
        item = sensorItem
    ) {
        managerListChoice(
            text = Resources.getString("custom_temp/${customTemp.customType}"),
            onItemClick = { onCustomTypeChange(it!!) },
            ids = CustomTempType.values().toList(),
            names = CustomTempType.values().map {
                Resources.getString("custom_temp/$it")
            },
            addNoneItem = false
        )
        managerListChoice(
            text = Resources.getString("custom_temp/add_temp"),
            onItemClick = { onAddTempSensor(it!!) },
            ids = sensorList.map { it.id },
            names = sensorList.map { it.libName },
            painterType = PainterType.ADD,
            addNoneItem = false
        )

        customTemp.sensorIdList.forEach { id ->
            selectedSensor(
                name = sensorList.first { it.id == id }.libName,
                id = id,
                onRemove = onRemoveTemp
            )
        }
    }
}


@Composable
private fun selectedSensor(
    name: String,
    id: Long,
    onRemove: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = Resources.getIcon("radio_button_unchecked"),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            managerText(
                text = name,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        IconButton(
            onClick = { onRemove(id) }
        ) {
            Icon(
                painter = Resources.getIcon("close"),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}