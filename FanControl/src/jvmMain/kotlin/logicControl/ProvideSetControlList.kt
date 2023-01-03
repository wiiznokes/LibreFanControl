package logicControl

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.UnspecifiedTypeException
import model.hardware.Sensor
import model.item.Control
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.behavior.Linear
import utils.filterWithPreviousIndex


class ProvideSetControlList(
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList,
    private val controlList: SnapshotStateList<Control> = State.controlList,
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
) {
    fun getSetControlList(controlsHasChangeMarker: MutableState<Boolean>): List<SetControlModel> {

        val finalSetControlList = mutableListOf<SetControlModel>()

        if (controlsHasChangeMarker.value) {
            handleControlChange(finalSetControlList)
        } else {
            handleControlShouldBeSet(finalSetControlList)
        }

        return finalSetControlList
    }

    private fun handleControlChange(setControlList: MutableList<SetControlModel>) {

        for (i in controlList.indices) {
            val control = controlList[i]

            setControlList.add(
                if (isControlShouldBeSet(control)) {
                    val res = findValueAndType(control)

                    val controlShouldBeSet = when (res.second) {
                        ItemType.BehaviorType.I_B_FLAT -> false
                        else -> true
                    }

                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = res.first == null,
                        value = res.first,
                        index = i,
                        controlShouldBeSet = controlShouldBeSet
                    )
                } else {
                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = true,
                        index = i,
                        controlShouldBeSet = false
                    )
                }
            )
        }
    }

    private fun handleControlShouldBeSet(setControlList: MutableList<SetControlModel>) {

        filterWithPreviousIndex(
            list = controlList,
            predicate = { it.controlShouldBeSet }
        ) label@{ index, control ->
            /*
            continue keyword of forEach loop
            we only return from the lambda expression
            (control here)
            */
            val value = findValue(control) ?: return@label

            setControlList.add(
                SetControlModel(
                    libIndex = control.libIndex,
                    isAuto = false,
                    value = value,
                    index = index,
                    controlShouldBeSet = true
                )
            )
        }
    }


    /**
     * @return value found if we can calculate it
     */
    private fun findValue(control: Control): Int? {

        val behaviorIndex = behaviorList.indexOfFirst {
            it.itemId == control.behaviorId
        }
        val behavior = behaviorList[behaviorIndex]

        return when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> (behavior.extension as Flat).value

            ItemType.BehaviorType.I_B_LINEAR -> valueLinear(behavior.extension as Linear, tempList)

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw UnspecifiedTypeException()
        }
    }

    /**
     * use to know if we should reset control at each iteration
     * @return Pair of (value, Behavior Type)
     */
    private fun findValueAndType(control: Control): Pair<Int?, ItemType.BehaviorType> {
        val behaviorIndex = behaviorList.indexOfFirst {
            it.itemId == control.behaviorId
        }
        val behavior = behaviorList[behaviorIndex]

        return when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT ->
                Pair(
                    (behavior.extension as Flat).value,
                    ItemType.BehaviorType.I_B_FLAT
                )

            ItemType.BehaviorType.I_B_LINEAR ->
                Pair(
                    valueLinear(behavior.extension as Linear, tempList),
                    ItemType.BehaviorType.I_B_LINEAR
                )

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw UnspecifiedTypeException()
        }
    }
}