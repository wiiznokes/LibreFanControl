package ui.screen.itemsList.sensor.body.tempList

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import model.item.ICustomTemp
import model.item.ITemp
import ui.screen.itemsList.sensor.baseSensorBody
import ui.utils.Resources


private val viewModel: TempVM = TempVM()

fun LazyListScope.tempBodyList() {
    itemsIndexed(viewModel.iTemps) { index, iTemp ->

        when (iTemp) {
            is ITemp -> tempBody(
                iTemp = iTemp,
                index = index
            )

            is ICustomTemp -> customTempBody(
                iCustomTemp = iTemp,
                index = index
            )
        }
    }
}


@Composable
private fun tempBody(
    iTemp: ITemp,
    index: Int,
) {

    val sensor = if (iTemp.hTempId.value != null) {
        viewModel.hTemps.find {
            it.id == iTemp.hTempId.value
        }
    } else null

    baseSensorBody(
        icon = Resources.getIcon("items/thermometer24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        sensorName = sensor?.name,
        sensorValue = "${sensor?.value?.value ?: 0} ${Resources.getString("unity/degree")}",
        sensorList = viewModel.hTemps,
        onItemClick = { viewModel.setTemp(index, it) },
        iSensor = iTemp
    )
}

@Composable
private fun customTempBody(
    iCustomTemp: ICustomTemp,
    index: Int,
) {
    baseCustomTempBody(
        onEditClick = { viewModel.remove(index) },
        onNameChange = { viewModel.setName(it, index) },
        hTemps = viewModel.hTemps,
        iCustomTemp = iCustomTemp,
        onCustomTypeChange = { viewModel.setCustomType(it, index) },
        onAddTempSensor = { viewModel.addTempCustom(it, index) },
        onRemoveTemp = { viewModel.removeTempCustom(it, index) }
    )
}
