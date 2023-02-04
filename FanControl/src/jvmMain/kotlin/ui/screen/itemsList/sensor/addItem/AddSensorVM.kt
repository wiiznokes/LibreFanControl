package ui.screen.itemsList.sensor.addItem

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.item.sensor.CustomTemp
import model.item.sensor.Fan
import model.item.sensor.SensorItem
import model.item.sensor.Temp
import ui.utils.Resources
import utils.Id.Companion.getAvailableId
import utils.Name.Companion.getAvailableName

class AddSensorVM(
    private val fanItemList: SnapshotStateList<SensorItem> = State.iFans,
    private val tempItemList: SnapshotStateList<SensorItem> = State.iTemps,
) {
    fun addFan() {
        fanItemList.add(
            SensorItem(
                name = getAvailableName(
                    names = fanItemList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/fan_name")
                ),
                type = ItemType.SensorType.I_S_FAN,
                id = getAvailableId(
                    ids = fanItemList.map { item ->
                        item.id
                    }
                ),
                extension = Fan()
            )
        )
    }

    fun addTemp() {
        tempItemList.add(
            SensorItem(
                name = getAvailableName(
                    names = tempItemList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/temp_name")
                ),
                type = ItemType.SensorType.I_S_TEMP,
                id = getAvailableId(
                    ids = tempItemList.map { item ->
                        item.id
                    }
                ),
                extension = Temp()
            )
        )
    }

    fun addCustomTemp() {
        tempItemList.add(
            SensorItem(
                name = getAvailableName(
                    names = tempItemList.map { item ->
                        item.name
                    },
                    prefix = Resources.getString("default/custom_temp_name")
                ),
                type = ItemType.SensorType.I_S_CUSTOM_TEMP,
                id = getAvailableId(
                    ids = tempItemList.map { item ->
                        item.id
                    },
                    positive = false
                ),
                extension = CustomTemp()
            )
        )
    }
}