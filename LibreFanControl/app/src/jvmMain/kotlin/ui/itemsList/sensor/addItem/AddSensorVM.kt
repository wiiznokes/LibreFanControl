package ui.screen.itemsList.sensor.addItem

import FState
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.*
import model.item.BaseI.Companion.getAvailableId
import model.item.BaseI.Companion.getAvailableName
import utils.Resources

class AddSensorVM(
    private val iFans: SnapshotStateList<IFan> = FState.iFans,
    private val iTemps: SnapshotStateList<BaseITemp> = FState.iTemps,
) {
    fun addFan() {
        iFans.add(
            IFan(
                name = getAvailableName(
                    list = iFans.map { item ->
                        item.name.value
                    },
                    prefix = Resources.getString("default/fan_name")
                ),
                id = getAvailableId(
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
                name = getAvailableName(
                    list = iTemps.map { item ->
                        item.name.value
                    },
                    prefix = Resources.getString("default/temp_name")
                ),
                id = getAvailableId(
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
                name = getAvailableName(
                    list = iTemps.map { item ->
                        item.name.value
                    },
                    prefix = Resources.getString("default/custom_temp_name")
                ),
                id = getAvailableId(
                    list = iTemps.map { item ->
                        item.id
                    },
                    prefix = BaseI.ICustomTempPrefix
                )
            )
        )
    }
}