package logicControl


import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val tempList: StateFlow<SnapshotStateList<Sensor>> = State._tempList.asStateFlow(),
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val behaviorItemList: StateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList.asStateFlow(),
    private val _controlsChange: MutableStateFlow<Boolean> = State._controlsChange
) {
    private val controlsChange = _controlsChange.asStateFlow()
    private val controlItemList = _controlItemList.asStateFlow()


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

        controlItemList.value.forEachIndexed { index, control ->

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
            list = controlItemList.value,
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
        val behavior = behaviorItemList.value.find { behavior ->
            behavior.itemId == control.behaviorId
        }!!

        return when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> {
                (behavior.extension as FlatBehavior).value
            }

            ItemType.BehaviorType.I_B_LINEAR -> valueLinear(behavior.extension as LinearBehavior, tempList.value)

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
                        _controlsChange.value = false
                        println("_controlsChange set to false in App")
                        controlsHasChangeMarker.value = false
                        setControlList.forEach { model ->
                            _controlItemList.update {
                                it[model.index].controlShouldBeSet = model.controlShouldBeSet
                                it
                            }
                            setControl(model.libIndex, model.isAuto, model.value)
                        }
                    }
                }
            }

            // normal case of update
            false -> {
                setControlList.forEach { model ->
                    _controlItemList.update {
                        it[model.index].controlShouldBeSet = model.controlShouldBeSet
                        it
                    }
                    setControl(model.libIndex, model.isAuto, model.value)
                }
            }
        }
        return shouldDelay
    }
}