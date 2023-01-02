package ui.screen.itemsList.sensor.addItem

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.SensorItem
import ui.utils.Resources
import utils.getAvailableId
import utils.getAvailableName

class AddSensorVM(
    private val fanItemList: SnapshotStateList<SensorItem> = State.fanItemList,
    private val tempItemList: SnapshotStateList<SensorItem> = State.tempItemList,
) {
    fun addFan() {
        val name = getAvailableName(
            names = fanItemList.map { item ->
                item.name
            },
            prefix = Resources.getString("default/fan_name")
        )


        fanItemList.add(
            SensorItem(
                name = name,
                type = ItemType.SensorType.I_S_FAN,
                itemId = getAvailableId(
                    ids = fanItemList.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }

    fun addTemp() {
        val name = getAvailableName(
            names = tempItemList.map { item ->
                item.name
            },
            prefix = Resources.getString("default/temp_name")
        )

        tempItemList.add(
            SensorItem(
                name = name,
                type = ItemType.SensorType.I_S_TEMP,
                itemId = getAvailableId(
                    ids = tempItemList.map { item ->
                        item.itemId
                    }
                )
            )
        )
    }
}