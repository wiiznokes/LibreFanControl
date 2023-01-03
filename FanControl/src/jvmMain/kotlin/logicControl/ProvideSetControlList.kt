package logicControl

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.UnspecifiedTypeException
import model.hardware.Sensor
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import utils.filterWithPreviousIndex


class ProvideSetControlList(
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList,
    private val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
    private val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
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

        controlItemList.forEachIndexed { index, control ->

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
                        index = index,
                        controlShouldBeSet = controlShouldBeSet
                    )
                } else {
                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = true,
                        index = index,
                        controlShouldBeSet = false
                    )
                }
            )
        }
    }

    private fun handleControlShouldBeSet(setControlList: MutableList<SetControlModel>) {

        filterWithPreviousIndex(
            list = controlItemList,
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
     * if linear behavior, we set the value found
     * @return value found if we can calculate it
     */
    private fun findValue(control: ControlItem): Int? {

        val behaviorIndex = behaviorItemList.indexOfFirst {
            it.itemId == control.behaviorId
        }
        val behavior = behaviorItemList[behaviorIndex]

        return when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> {
                (behavior.extension as FlatBehavior).value
            }

            ItemType.BehaviorType.I_B_LINEAR -> {
                val value = valueLinear(behavior.extension as LinearBehavior, tempList)

                behaviorItemList[behaviorIndex] = behaviorItemList[behaviorIndex].copy(
                    extension = (behaviorItemList[behaviorIndex].extension as LinearBehavior).copy(
                        value = value ?: 0
                    )
                )
                value
            }

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw UnspecifiedTypeException()
        }
    }

    /**
     * use to know if we should reset control at each iteration
     * if linear behavior, we set the value found
     * @return Pair of (value, Behavior Type)
     */
    private fun findValueAndType(control: ControlItem): Pair<Int?, ItemType.BehaviorType> {
        val behaviorIndex = behaviorItemList.indexOfFirst {
            it.itemId == control.behaviorId
        }
        val behavior = behaviorItemList[behaviorIndex]

        return when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT ->
                Pair((behavior.extension as FlatBehavior).value, ItemType.BehaviorType.I_B_FLAT)


            ItemType.BehaviorType.I_B_LINEAR ->  {
                val value = valueLinear(behavior.extension as LinearBehavior, tempList)
                behaviorItemList[behaviorIndex] = behaviorItemList[behaviorIndex].copy(
                    extension = (behaviorItemList[behaviorIndex].extension as LinearBehavior).copy(
                        value = value ?: 0
                    )
                )
                Pair(value, ItemType.BehaviorType.I_B_LINEAR)
            }

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw UnspecifiedTypeException()
        }
    }
}