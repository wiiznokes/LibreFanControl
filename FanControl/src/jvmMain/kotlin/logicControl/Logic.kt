package logicControl


import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import external.ExternalManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import model.item.control.Control
import settings.SettingsModel
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
    private val controlList: SnapshotStateList<Control> = State.controlList,
    private val controlChangeList: SnapshotStateList<Boolean> = State.controlChangeList,
    private val settings: StateFlow<SettingsModel> = State.settings.asStateFlow(),
    private val externalManager: ExternalManager,
    private val mutex: Mutex = State.controlChangeMutex
) {

    private var shouldDelay: Boolean = true

    /**
     * controlsChange is updated here, in ControlViewModel, in ConfigurationViewModel  and
     * in BehaviorViewModel.
     *
     * ControlViewModel, ConfigurationViewModel and BehaviorViewModel are in the same coroutine,
     * but here, we are in another coroutine.
     * We use a marker because control may change after we calculate values to set.
     *
     * So we recalculate one time before setting the values
     */
    private val controlChangeListMarker = controlChangeList.toMutableList()

    private val provideSetControlList = ProvideSetControlList()


    /**
     * - update last values
     * - get a list of controls we need to set, in function of control change variable
     * - set controls
     * - delay if no error or if controls hasn't change
     */
    suspend fun update() {
        externalManager.updateFanList()
        externalManager.updateTempList()
        externalManager.updateControlList()

        /**
         * we catch exception because another thread can modify
         * state value, for example, if we remove an item
         * that can lead to null pointer exception.
         * This is not a problem because we can recalculate
         */
        val setControlList: List<SetControlModel>? = try {
            provideSetControlList.getSetControlList(controlChangeListMarker)
        } catch (e: NullPointerException) {
            println("NullPointerException in logic")
            null
        } catch (e: IndexOutOfBoundsException) {
            println("IndexOutOfBoundsException in logic")
            null
        }

        if (setControlList == null) {
            for (i in controlChangeList.indices) {
                if (controlChangeList[i])
                    controlChangeListMarker[i] = true
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
     * set controls witch was previously active to auto
     * if controls change is true, we set all controls to auto
     */
    fun finish() {
        controlList.forEachIndexed { index, control ->
            if (controlChangeList[index])
                externalManager.setControl(control.libIndex, true)
            else {
                if (isControlShouldBeSet(control)) {
                    externalManager.setControl(control.libIndex, true)
                }
            }
        }
    }


    /**
     * logic of the update process
     * if control has change, we update only if controlsHasChangeMarker is true
     * if not, we reiterate one time.
     *
     * @return shouldDelay
     */
    private suspend fun setControlLogic(
        setControlList: List<SetControlModel>
    ): Boolean {

        var shouldDelay = true

        for (i in controlChangeList.indices) {
            when (controlChangeList[i]) {
                true -> {
                    when (controlChangeListMarker[i]) {
                        // control change in this iteration
                        false -> {
                            controlChangeListMarker[i] = true
                            shouldDelay = false
                        }
                        // control change in the previous iteration
                        true -> {
                            mutex.lock()
                            controlChangeList[i] = false
                            mutex.unlock()
                            controlChangeListMarker[i] = false
                            val model = setControlList.first { it.index == i }
                            controlList[i].controlShouldBeSet = model.controlShouldBeSet
                            externalManager.setControl(model.libIndex, model.isAuto, model.value)
                        }
                    }
                }
                // normal case of update
                false -> {
                    val model = setControlList.firstOrNull { it.index == i }
                    if (model != null)
                        externalManager.setControl(model.libIndex, model.isAuto, model.value)
                }
            }
        }

        return shouldDelay
    }
}


fun isControlShouldBeSet(control: Control): Boolean =
    !control.isAuto && control.behaviorId != null


fun isControlChange(previousControl: Control, newControl: Control): Boolean {

    if (isControlShouldBeSet(previousControl) != isControlShouldBeSet(newControl))
        return true

    if (isControlShouldBeSet(newControl)) {
        if (previousControl.behaviorId != newControl.behaviorId)
            return true
    }

    return false
}
