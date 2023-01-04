package logicControl


import State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import external.ExternalManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.SettingsModel
import model.item.Control
import java.lang.IndexOutOfBoundsException
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
    private val controlsChange: MutableStateFlow<Boolean> = State.controlsChange,
    private val settings: StateFlow<SettingsModel> = State.settings.asStateFlow(),
    private val externalManager: ExternalManager
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
    private val controlsHasChangeMarker = mutableStateOf(controlsChange.value)

    private val provideSetControlList = ProvideSetControlList()


    /**
     * - update last values
     * - get a list of controls we need to set, in function of control change variable
     * - set controls
     * - delay if no error or if controls hasn't change
     */
    suspend fun update() {
        externalManager.updateFan()
        externalManager.updateTemp()
        externalManager.updateControl()

        /**
         * we catch exception because another thread can modify
         * state value, for example, if we remove an item
         * that can lead to null pointer exception.
         * This is not a problem because we can recalculate
         */
        val setControlList: List<SetControlModel>? = try {
            provideSetControlList.getSetControlList(controlsHasChangeMarker)
        } catch (e: NullPointerException) {
            println("NullPointerException in logic")
            null
        } catch (e: IndexOutOfBoundsException) {
            println("IndexOutOfBoundsException in logic")
            null
        }

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
     * set controls witch was previously active to auto
     * if controls change is true, we set all controls to auto
     */
    fun finish() {
        controlList.forEach {
            if (controlsChange.value)
                externalManager.setControl(it.libIndex, true)
            else {
                if (isControlShouldBeSet(it)) {
                    externalManager.setControl(it.libIndex, true)
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

                            controlList[model.index].controlShouldBeSet = model.controlShouldBeSet

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
}


fun isControlShouldBeSet(control: Control): Boolean =
    !control.isAuto && control.behaviorId != null


fun isControlChange(previousControl: Control, newControl: Control): Boolean =
    isControlShouldBeSet(previousControl) != isControlShouldBeSet(newControl)
