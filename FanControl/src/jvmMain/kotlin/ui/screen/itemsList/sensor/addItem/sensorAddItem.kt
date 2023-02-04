package ui.screen.itemsList.sensor.addItem

import androidx.compose.foundation.lazy.LazyListScope
import ui.screen.itemsList.baseItemAddItem
import ui.utils.Resources

val viewModel = AddSensorVM()


fun LazyListScope.sensorAddItemList() {

    // fan
    item {
        baseItemAddItem(
            icon = Resources.getIcon("items/toys_fan24"),
            name = Resources.getString("add_item/fan_name"),
            onEditClick = { viewModel.addFan() }
        ) {

        }
    }

    // temp
    item {
        baseItemAddItem(
            icon = Resources.getIcon("items/thermometer24"),
            name = Resources.getString("add_item/temp_name"),
            onEditClick = { viewModel.addTemp() }
        ) {

        }
    }

    // custom temp
    item {
        baseItemAddItem(
            icon = Resources.getIcon("items/thermometer24"),
            name = Resources.getString("add_item/temp_name"),
            onEditClick = { viewModel.addCustomTemp() }
        ) {

        }
    }
}