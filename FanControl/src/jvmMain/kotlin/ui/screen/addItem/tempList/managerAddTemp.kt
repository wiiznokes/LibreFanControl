package ui.screen.addItem.tempList


import Source
import androidx.compose.runtime.Composable
import model.hardware.sensor.Temp
import ui.component.baseSensor
import ui.utils.Resources

@Composable
fun addTemp(
    temp: Temp,
    index: Int
) {

    val viewModel = AddTempViewModel()


    baseSensor(
        iconPainter = Resources.getIcon("add"),
        iconContentDescription = "",
        name = temp.name,
        onEditClick = {
            viewModel.add(index)
        },
        source = Source.ADD,
        sensorName = temp.libName
    )
}



