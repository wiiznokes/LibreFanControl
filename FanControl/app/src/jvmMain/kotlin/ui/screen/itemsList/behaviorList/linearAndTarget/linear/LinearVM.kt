package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import FState
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.HTemp
import model.item.BaseI
import model.item.BaseI.Companion.getAvailableId
import model.item.BaseI.Companion.getAvailableName
import model.item.BaseITemp
import model.item.ILinear
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.screen.itemsList.behaviorList.linearAndTarget.LinAndTarParams
import ui.screen.itemsList.behaviorList.linearAndTarget.numberChoiceFinalValue
import ui.utils.Resources


enum class LinearParams : LinAndTarParams {
    MIN_TEMP,
    MAX_TEMP,
    MIN_FAN_SPEED,
    MAX_FAN_SPEED
}


class LinearVM(
    val hTemps: SnapshotStateList<HTemp> = FState.hTemps,
    val iTemps: SnapshotStateList<BaseITemp> = FState.iTemps,
) : BaseBehaviorVM() {


    fun setTemp(index: Int, hTempId: String?) {
        (iBehaviors[index] as ILinear).hTempId.value = hTempId
    }

    fun increase(index: Int, type: LinearParams): String {
        return updateValue(index, type) {
            it + 1
        }
    }

    fun decrease(index: Int, type: LinearParams): String {
        return updateValue(index, type) {
            it - 1
        }
    }

    fun onChange(index: Int, value: Int, type: LinearParams): String {
        return updateValue(index, type) {
            value
        }
    }


    /**
     * @param value used to make operation on the previous value before calculation of the final value
     */
    private fun updateValue(index: Int, type: LinearParams, value: (Int) -> Int): String {
        val finalValue: Int

        with(iBehaviors[index] as ILinear) {
            when (type) {
                LinearParams.MIN_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(this.minTemp.value))
                    this.minTemp.value = finalValue
                }

                LinearParams.MAX_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(this.maxTemp.value))
                    this.maxTemp.value = finalValue
                }

                LinearParams.MIN_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(this.minFanSpeed.value))
                    this.minFanSpeed.value = finalValue
                }

                LinearParams.MAX_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(this.maxFanSpeed.value))
                    this.maxFanSpeed.value = finalValue
                }
            }
        }
        return finalValue.toString()
    }

    fun defaultLinear() = ILinear(
        name = getAvailableName(
            list = iBehaviors.map { item ->
                item.name.value
            },
            prefix = Resources.getString("default/linear_name")
        ),
        id = getAvailableId(
            list = iBehaviors.map { item ->
                item.id
            },
            prefix = BaseI.ILinearPrefix
        )
    )
}