package ui.screen.itemsList.sensor.addItem

import androidx.compose.foundation.lazy.LazyListScope
import ui.screen.itemsList.baseItemAddItem
import utils.Resources

val viewModel = AddSensorVM()


fun LazyListScope.sensorAddItemList() {

    // fan
    item {
        baseItemAddItem(
            icon = Resources.getIcon("items/toys_fan24"),
            name = Resources.getString("add_item/name/fan"),
            onEditClick = { viewModel.addFan() },
            text = Resources.getString("add_item/info/fan")
        )
    }

    // temp
    item {
        baseItemAddItem(
            icon = Resources.getIcon("items/thermometer24"),
            name = Resources.getString("add_item/name/temp"),
            onEditClick = { viewModel.addTemp() },
            text = Resources.getString("add_item/info/temp")
        )
    }

    // custom temp
    item {
        baseItemAddItem(
            icon = Resources.getIcon("items/thermometer24"),
            name = Resources.getString("add_item/name/custom_temp"),
            onEditClick = { viewModel.addCustomTemp() },
            text = Resources.getString("add_item/info/custom_temp")
        )
    }
}