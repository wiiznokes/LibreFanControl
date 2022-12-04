package ui.screen.addItem.fanList

import Source
import androidx.compose.runtime.Composable
import model.hardware.sensor.Fan
import ui.component.baseSensor
import ui.screen.body.fanList.fan.AddFanViewModel
import ui.utils.Resources

@Composable
fun addFan(
    fan: Fan,
    index: Int
) {
    val viewModel = AddFanViewModel()

    baseSensor(
        imageVector = Resources.getIcon("add"),
        iconContentDescription = "",
        name = fan.name,
        onEditClick = {
            viewModel.add(index)
        },
        source = Source.ADD,
        sensorName = fan.libName
    )
}
