package logicControl


import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.ItemType
import model.UnspecifiedTypeException
import model.hardware.Sensor
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import utils.filterWithPreviousIndex


data class SetControlModel(
    val libIndex: Int,
    val isAuto: Boolean,
    val value: Int? = null,
    val index: Int,
    val controlShouldBeSet: Boolean
)

class Logic(
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList,
    private val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
    private val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
    private val controlsChange: MutableStateFlow<Boolean> = State.controlsChange
) {

    fun getSetControlList(
        controlsChange: Boolean
    ): List<SetControlModel> {

        val finalSetControlList = mutableListOf<SetControlModel>()

        if (controlsChange) {
            handleControlChange(finalSetControlList)
        } else {
            handleControlShouldBeSet(finalSetControlList)
        }

        return finalSetControlList
    }

    /*

*/
    private fun handleControlChange(setControlList: MutableList<SetControlModel>) {

        controlItemList.forEachIndexed { index, control ->

            setControlList.add(
                if (control.isAuto || control.behaviorId == null) {
                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = true,
                        index = index,
                        controlShouldBeSet = false
                    )
                } else {
                    val value = findValue(control)
                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = value == null,
                        value = value,
                        index = index,
                        controlShouldBeSet = true
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


    private fun findValue(control: ControlItem): Int? {
        val behavior = behaviorItemList.find { behavior ->
            behavior.itemId == control.behaviorId
        }!!

        return when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> {
                (behavior.extension as FlatBehavior).value
            }

            ItemType.BehaviorType.I_B_LINEAR -> valueLinear(behavior.extension as LinearBehavior, tempList)

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw UnspecifiedTypeException()
        }
    }


    fun update(
        setControlList: List<SetControlModel>,
        controlsHasChangeMarker: MutableState<Boolean>,
        setControl: (Int, Boolean, Int?) -> Unit
    ): Boolean {

        var shouldDelay = true

        when (controlsChange.value) {
            true -> {
                when (controlsHasChangeMarker.value) {
                    // control change in this iteration
                    false -> {
                        println("control has change, marker not enable")
                        controlsHasChangeMarker.value = true
                        shouldDelay = false
                    }
                    // control change in the previous iteration
                    true -> {
                        println("control has change, marker enable")
                        controlsChange.value = false
                        println("_controlsChange set to false in App")
                        controlsHasChangeMarker.value = false
                        setControlList.forEach { model ->

                            controlItemList[model.index].controlShouldBeSet = model.controlShouldBeSet

                            setControl(model.libIndex, model.isAuto, model.value)
                        }
                    }
                }
            }

            // normal case of update
            false -> {
                setControlList.forEach { model ->
                    controlItemList[model.index].controlShouldBeSet = model.controlShouldBeSet

                    setControl(model.libIndex, model.isAuto, model.value)
                }
            }
        }
        return shouldDelay
    }
}