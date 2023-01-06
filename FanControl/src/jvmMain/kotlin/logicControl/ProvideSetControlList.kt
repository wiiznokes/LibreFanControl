package logicControl

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import logicControl.behaviorLogic.BehaviorLogic
import model.ItemType
import model.item.behavior.Behavior
import model.item.control.Control
import utils.filterWithPreviousIndex


class ProvideSetControlList(
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


    private val behaviorLogic = BehaviorLogic()

    /**
     * @return value found if we can calculate it
     */
    private fun findValue(control: Control): Int? {

        val behaviorIndex = behaviorList.indexOfFirst {
            it.id == control.behaviorId
        }
        val behavior = behaviorList[behaviorIndex]

        return behaviorLogic.getValue(behavior.extension, behaviorIndex)
    }

    /**
     * use to know if we should reset control at each iteration
     * @return Pair of (value, Behavior Type)
     */
    private fun findValueAndType(control: Control): Pair<Int?, ItemType.BehaviorType> {
        val behaviorIndex = behaviorList.indexOfFirst {
            it.id == control.behaviorId
        }
        val behavior = behaviorList[behaviorIndex]

        return Pair(
            behaviorLogic.getValue(behavior.extension, behaviorIndex),
            behavior.type
        )
    }
}