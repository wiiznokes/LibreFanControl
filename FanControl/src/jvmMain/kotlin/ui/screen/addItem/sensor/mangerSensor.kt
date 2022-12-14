package ui.screen.addItem.sensor

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import ui.component.baseSensorAddItem
import ui.utils.Resources

val viewModel = AddSensorViewModel()

@Composable
fun managerAddSensor() {
    LazyColumn {
        // fan
        item {
            baseSensorAddItem(
                iconPainter = Resources.getIcon("toys_fan"),
                iconContentDescription = Resources.getString("ct/fan"),
                name = Resources.getString("add_item/fan_name"),
                onEditClick = {
                    viewModel.addFan()
                },
                sensorName = Resources.getString("add_item/choose_sensor"),
                sensorValue = "1000 ${Resources.getString("unity/rpm")}"
            )
        }

        // temp
        item {
            baseSensorAddItem(
                iconPainter = Resources.getIcon("thermometer"),
                iconContentDescription = Resources.getString("ct/temp"),
                name = Resources.getString("add_item/temp_name"),
                onEditClick = {
                    viewModel.addTemp()
                },
                sensorName = Resources.getString("add_item/choose_sensor"),
                sensorValue = "50 ${Resources.getString("unity/degree")}"
            )
        }
    }
}