package ui.screen.itemsList.sensor.body.tempList

import FState
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.HTemp
import model.item.*
import model.item.BaseI.Companion.checkNameValid

class TempVM(
    val iTemps: SnapshotStateList<BaseITemp> = FState.iTemps,
    val hTemps: SnapshotStateList<HTemp> = FState.hTemps,
    private val iBehaviors: SnapshotStateList<BaseIBehavior> = FState.iBehaviors,
) {


    fun removeCustom(index: Int) {

        val itemp = iTemps[index]

        /**
         * only for custom sensor.
         * we need to remove id in behaviors if necessary
         */

        if (BaseI.getPrefix(itemp.id) == BaseI.ICustomTempPrefix) {
            for (i in iBehaviors.indices) {
                with(iBehaviors[i]) {
                    when (this) {
                        is ILinear -> {
                            if (hTempId.value == itemp.id) {
                                hTempId.value = null
                            }
                        }

                        is ITarget -> {
                            if (hTempId.value == itemp.id) {
                                hTempId.value = null
                            }
                        }

                        else -> {}
                    }
                }

            }
        }

        iTemps.removeAt(index)
    }

    fun setTemp(index: Int, hTempId: String?) {
        with(iTemps[index] as ITemp) {
            this.hTempId.value = hTempId
        }
    }


    fun setName(name: String, index: Int) {
        checkNameValid(
            names = iTemps.map { item ->
                item.name.value
            },
            name = name,
            index = index
        )
        iTemps[index].name.value = name
    }

    fun setCustomType(type: CustomTempType, index: Int) {
        with(iTemps[index] as ICustomTemp) {
            customTempType.value = type
        }
    }

    fun addTempCustom(hTempId: String, index: Int) {
        with(iTemps[index] as ICustomTemp) {
            hTempIds.add(hTempId)
        }
    }

    fun removeTempCustom(hTempId: String, index: Int) {
        with(iTemps[index] as ICustomTemp) {
            hTempIds.remove(hTempId)
        }
    }
}