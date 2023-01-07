package ui.screen.itemsList.sensor.body.tempList

import State
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import model.hardware.Sensor
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType
import model.item.sensor.SensorItem
import ui.component.PainterType
import ui.component.managerExpandItem
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources


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
            text = Resources.getString("custom_temp/${customTemp.customTempType}"),
            onItemClick = { onCustomTypeChange(it!!) },
            ids = CustomTempType.values().toList(),
            names = CustomTempType.values().map {
                Resources.getString("custom_temp/$it")
            },
            addNoneItem = false
        )


        val expanded = remember(
            sensorItem.id,
            State.settings.collectAsState().value.configId
        ) {
            mutableStateOf(false)
        }

        managerExpandItem(
            value = customTemp.value,
            color = MaterialTheme.colorScheme.onSurface,
            expanded = expanded,
            suffix = Resources.getString("unity/degree")
        )

        if (expanded.value) {
            val filterListSensor = sensorList.filter {
                !customTemp.sensorIdList.contains(it.id)
            }

            managerListChoice(
                text = Resources.getString("custom_temp/add_temp"),
                onItemClick = { onAddTempSensor(it!!) },
                ids = filterListSensor.map { it.id },
                names = filterListSensor.map { it.libName },
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
}


@Composable
private fun selectedSensor(
    name: String,
    id: Long,
    onRemove: (Long) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                IconButton(
                    onClick = { onRemove(id) }
                ) {
                    Icon(
                        modifier = Modifier.scale(0.7f).requiredSize(40.dp),
                        painter = Resources.getIcon("close"),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        modifier = Modifier.scale(0.7f).requiredSize(30.dp),
                        painter = Resources.getIcon("radio_button_unchecked"),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    managerText(
                        text = name,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}