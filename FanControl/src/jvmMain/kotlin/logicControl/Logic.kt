package logicControl


import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val controlItemList: StateFlow<SnapshotStateList<ControlItem>> = State._controlItemList.asStateFlow(),
    private val behaviorItemList: StateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList.asStateFlow(),
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

        controlItemList.value.forEachIndexed { index, control ->

            setControlList.add(
                if (control.isAuto || control.behaviorId == null) {
                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = true,
                        index = index,
                        controlShouldBeSet = false
                    ).apply { println(this) }
                } else {
                    val value = findValue(control)
                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = value == null,
                        value = value,
                        index = index,
                        controlShouldBeSet = true
                    ).apply { println(this) }
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
}