package ui.screen.itemsList.sensor.body.tempList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.ItemType
import model.item.sensor.SensorItem
import model.item.sensor.Temp
import ui.screen.itemsList.sensor.baseSensorBody
import ui.utils.Resources


private val viewModel: TempVM = TempVM()

fun LazyListScope.tempBodyList() {
    itemsIndexed(viewModel.tempItemList) { index, temp ->
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
    sensorItem: SensorItem,
    index: Int,
) {


    val sensor = if ((sensorItem.extension as Temp).sensorId != null) {
        viewModel.tempList.find {
            it.id == sensorItem.extension.sensorId
        }
    } else null

    baseSensorBody(
        iconPainter = Resources.getIcon("items/thermometer"),
        iconContentDescription = Resources.getString("ct/temp"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        sensorName = sensor?.libName,
        sensorValue = "${sensor?.value ?: 0} ${Resources.getString("unity/degree")}",
        sensorList = viewModel.tempList,
        onItemClick = { viewModel.setTemp(index, it) },
        sensorItem = sensorItem
    )
}

@Composable
private fun customTempBody(
    sensorItem: SensorItem,
    index: Int,
) {
    baseCustomTempBody(
        onEditClick = { viewModel.remove(index) },
        onNameChange = { viewModel.setName(it, index) },
        sensorList = viewModel.tempList,
        sensorItem = sensorItem,
        onCustomTypeChange = { viewModel.setCustomType(it, index) },
        onAddTempSensor = { viewModel.addTempCustom(it, index) },
        onRemoveTemp = { viewModel.removeTempCustom(it, index) }
    )
}
