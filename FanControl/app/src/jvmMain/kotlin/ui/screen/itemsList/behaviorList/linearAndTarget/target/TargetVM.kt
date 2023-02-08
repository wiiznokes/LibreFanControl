package ui.screen.itemsList.behaviorList.linearAndTarget.target


import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.HTemp
import model.item.BaseI
import model.item.BaseITemp
import model.item.ITarget
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.screen.itemsList.behaviorList.linearAndTarget.LinAndTarParams
import ui.screen.itemsList.behaviorList.linearAndTarget.numberChoiceFinalValue
import ui.utils.Resources


enum class TargetParams : LinAndTarParams {
    IDLE_TEMP,
    LOAD_TEMP,
    IDLE_FAN_SPEED,
    LOAD_FAN_SPEED
}

class TargetVM(
    val hTemps: SnapshotStateList<HTemp> = State.hTemps,
    val iTemps: SnapshotStateList<BaseITemp> = State.iTemps,
) : BaseBehaviorVM() {

    fun setTemp(index: Int, hTempId: String?) {
        (iBehaviors[index] as ITarget).hTempId.value = hTempId
    }

    fun increase(index: Int, type: TargetParams): String {
        return updateValue(index, type) {
            it + 1
        }
    }

    fun decrease(index: Int, type: TargetParams): String {
        return updateValue(index, type) {
            it - 1
        }
    }

    fun onChange(index: Int, value: Int, type: TargetParams): String {
        return updateValue(index, type) {
            value
        }
    }


    /**
     * @param value used to make operation on the previous value before calculation of the final value
     */
    private fun updateValue(index: Int, type: TargetParams, value: (Int) -> Int): String {
        val finalValue: Int

        with(iBehaviors[index] as ITarget) {
            when (type) {
                TargetParams.IDLE_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(this.idleTemp.value))
                    this.idleTemp.value = finalValue
                }

                TargetParams.LOAD_TEMP -> {
                    finalValue = numberChoiceFinalValue(value(this.loadTemp.value))
                    this.loadTemp.value = finalValue
                }

                TargetParams.IDLE_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(this.idleFanSpeed.value))
                    this.idleFanSpeed.value = finalValue
                }

                TargetParams.LOAD_FAN_SPEED -> {
                    finalValue = numberChoiceFinalValue(value(this.loadFanSpeed.value))
                    this.loadFanSpeed.value = finalValue
                }
            }
        }
        return finalValue.toString()
    }


    fun defaultTarget() = ITarget(
        name = BaseI.getAvailableName(
            list = iBehaviors.map { item ->
                item.name.value
            },
            prefix = Resources.getString("default/target_name")
        ),
        id = BaseI.getAvailableId(
            list = iBehaviors.map { item ->
                item.id
            },
            prefix = BaseI.ITargetPrefix
        )
    )
}