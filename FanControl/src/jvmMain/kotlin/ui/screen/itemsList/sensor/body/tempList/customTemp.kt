package ui.screen.itemsList.sensor.body.tempList

import State
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import model.hardware.Sensor
import model.item.sensor.CustomTemp
import model.item.sensor.CustomTempType
import model.item.sensor.SensorItem
import ui.component.PainterType
import ui.component.managerExpandItem
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemBody
import ui.theme.LocalColors
import ui.theme.LocalSpaces
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
        icon = Resources.getIcon("items/thermostat24"),
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

        Spacer(Modifier.height(LocalSpaces.current.medium))

        managerExpandItem(
            value = customTemp.value,
            color = LocalColors.current.onMainContainer,
            expanded = expanded,
            suffix = Resources.getString("unity/degree")
        ) {
            val filterListSensor = sensorList.filter {
                !customTemp.sensorIdList.contains(it.id)
            }

            Spacer(Modifier.height(LocalSpaces.current.small))

            managerListChoice(
                text = Resources.getString("custom_temp/add_temp"),
                onItemClick = { onAddTempSensor(it!!) },
                ids = filterListSensor.map { it.id },
                names = filterListSensor.map { it.name },
                painterType = PainterType.ADD,
                addNoneItem = false
            )

            customTemp.sensorIdList.forEach { id ->
                selectedSensor(
                    name = sensorList.first { it.id == id }.name,
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
    Spacer(Modifier.height(LocalSpaces.current.medium))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        managerText(
            text = name,
            color = LocalColors.current.onMainContainer,
            style = MaterialTheme.typography.bodySmall
        )

        Icon(
            modifier = Modifier
                .clickable { onRemove(id) },
            painter = Resources.getIcon("select/close/close20"),
            contentDescription = null,
            tint = LocalColors.current.onMainContainer
        )
    }
}