package logicControl


import State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import external.ExternalManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.ItemType
import model.SettingsModel
import model.UnspecifiedTypeException
import model.hardware.Sensor
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior
import utils.filterWithPreviousIndex
import kotlin.time.DurationUnit
import kotlin.time.toDuration


data class SetControlModel(
    val libIndex: Int,
    val isAuto: Boolean,
    val value: Int? = null,
    val index: Int,
    val controlShouldBeSet: Boolean
)

/**
 * class to calculate the logic of each control in function
 * of their behavior. controlShouldBeSet variable is used for
 * not calculate control witch are disabled or have no behavior id.
 * Each time:
 * - control isAuto variable behaviorId variable change
 * - a behavior witch was linked with a control is remove
 * - configuration change
 * controlsChange is set to true, and each control is recalculate and
 * set.
 */
class Logic(
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList,
    private val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
    private val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
    private val controlsChange: MutableStateFlow<Boolean> = State.controlsChange,
    private val settings: StateFlow<SettingsModel> = State.settings.asStateFlow(),
    private val externalManager: ExternalManager
) {

    private var shouldDelay: Boolean = true
    private val controlsHasChangeMarker = mutableStateOf(controlsChange.value)
    suspend fun update() {
        /*
            we catch exception because another thread can modify
            state value, for example, if we remove an item
            that can lead to null pointer exception.
            This is not a problem because we can recalculate
        */

        val setControlList: List<SetControlModel>? = try {
            getSetControlList()
        } catch (e: Exception) {
            if (e !is NullPointerException) e.printStackTrace()
            else println("null pointer exception in logic")
            null
        }

        externalManager.updateFan()
        externalManager.updateTemp()
        externalManager.updateControl()

        /*
            controlsChange is updated here, in ControlViewModel, in ConfigurationViewModel  and
            in BehaviorViewModel.

            ControlViewModel, ConfigurationViewModel and BehaviorViewModel are in the same coroutine,
            but here, we are in another coroutine.
            We use a marker because control may change after we calculate values to set.

            So we recalculate one time before setting the values
        */
        if (setControlList == null) {
            if (controlsChange.value) {
                controlsHasChangeMarker.value = true
            }
            shouldDelay = false
        } else {
            shouldDelay = setControlLogic(
                setControlList = setControlList
            )
        }

        if (shouldDelay)
            delay(settings.value.updateDelay.toDuration(DurationUnit.SECONDS))
    }


    /**
     * logic of the update process
     * if control has change, we update only if controlsHasChangeMarker is true
     * if not, we reiterate one time.
     *
     * @return shouldDelay
     */
    private fun setControlLogic(
        setControlList: List<SetControlModel>
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
                        controlsHasChangeMarker.value = false
                        setControlList.forEach { model ->

                            controlItemList[model.index].controlShouldBeSet = model.controlShouldBeSet

                            externalManager.setControl(model.libIndex, model.isAuto, model.value)
                        }
                    }
                }
            }

            // normal case of update
            false -> {
                setControlList.forEach { model ->
                    externalManager.setControl(model.libIndex, model.isAuto, model.value)
                }
            }
        }
        return shouldDelay
    }


    private fun getSetControlList(): List<SetControlModel> {

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
                if (control.isAuto || control.behaviorId == null) {
                    SetControlModel(
                        libIndex = control.libIndex,
                        isAuto = true,
                        index = index,
                        controlShouldBeSet = false
                    )
                } else {
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

    /**
     * use to know if we should reset control at each iteration
     * @return Pair of (value, Behavior Type)
     */
    private fun findValueAndType(control: ControlItem): Pair<Int?, ItemType.BehaviorType> {
        val behavior = behaviorItemList.find { behavior ->
            behavior.itemId == control.behaviorId
        }!!

        return when (behavior.type) {
            ItemType.BehaviorType.I_B_FLAT -> {
                Pair((behavior.extension as FlatBehavior).value, ItemType.BehaviorType.I_B_FLAT)
            }

            ItemType.BehaviorType.I_B_LINEAR -> Pair(
                valueLinear(behavior.extension as LinearBehavior, tempList),
                ItemType.BehaviorType.I_B_LINEAR
            )

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw UnspecifiedTypeException()
        }
    }
}