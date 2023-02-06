package ui.screen.itemsList.sensor.body.tempList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.ItemType
import model.item.sensor.SensorI
import model.item.sensor.Temp
import ui.screen.itemsList.sensor.baseSensorBody
import ui.utils.Resources


private val viewModel: TempVM = TempVM()

fun LazyListScope.tempBodyList() {
    itemsIndexed(viewModel.iTemps) { index, temp ->
        when (temp.type) {
            ItemType.SensorType.I_S_CUSTOM_TEMP -> customTempBody(
                sensorItem = temp,
                index = index
            )

            else -> tempBody(
                sensorItem = temp,
                index = index
            )
        }

    }
}


@Composable
private fun tempBody(
    sensorItem: SensorI,
    index: Int,
) {


    val sensor = if ((sensorItem.extension as Temp).hTempId != null) {
        viewModel.hTemps.find {
            it.id == sensorItem.extension.hTempId
        }
    } else null

    baseSensorBody(
        icon = Resources.getIcon("items/thermometer24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        sensorName = sensor?.name,
        sensorValue = "${sensor?.value ?: 0} ${Resources.getString("unity/degree")}",
        sensorList = viewModel.hTemps,
        onItemClick = { viewModel.setTemp(index, it) },
        sensorItem = sensorItem
    )
}

@Composable
private fun customTempBody(
    sensorItem: SensorI,
    index: Int,
) {
    baseCustomTempBody(
        onEditClick = { viewModel.remove(index) },
        onNameChange = { viewModel.setName(it, index) },
        hTemps = viewModel.hTemps,
        sensorItem = sensorItem,
        onCustomTypeChange = { viewModel.setCustomType(it, index) },
        onAddTempSensor = { viewModel.addTempCustom(it, index) },
        onRemoveTemp = { viewModel.removeTempCustom(it, index) }
    )
}
