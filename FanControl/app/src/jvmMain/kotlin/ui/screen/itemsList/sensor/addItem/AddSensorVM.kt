package ui.screen.itemsList.sensor.addItem

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.*
import model.item.BaseI.Companion.getAvailableString
import ui.utils.Resources

class AddSensorVM(
    private val iFans: SnapshotStateList<IFan> = State.iFans,
    private val iTemps: SnapshotStateList<BaseITemp> = State.iTemps,
) {
    fun addFan() {
        iFans.add(
            IFan(
                name = getAvailableString(
                    list = iFans.map { item ->
                        item.name.value
                    },
                    prefix = Resources.getString("default/fan_name")
                ),
                id = getAvailableString(
                    list = iFans.map { item ->
                        item.id
                    },
                    prefix = BaseI.IFanPrefix
                )
            )
        )
    }

    fun addTemp() {
        iTemps.add(
            ITemp(
                name = getAvailableString(
                    list = iTemps.map { item ->
                        item.name.value
                    },
                    prefix = Resources.getString("default/temp_name")
                ),
                id = getAvailableString(
                    list = iTemps.map { item ->
                        item.id
                    },
                    prefix = BaseI.ITempPrefix
                )
            )
        )
    }

    fun addCustomTemp() {
        iTemps.add(
            ICustomTemp(
                name = getAvailableString(
                    list = iTemps.map { item ->
                        item.name.value
                    },
                    prefix = Resources.getString("default/custom_temp_name")
                ),
                id = getAvailableString(
                    list = iTemps.map { item ->
                        item.id
                    },
                    prefix = BaseI.ICustomTempPrefix
                )
            )
        )
    }
}