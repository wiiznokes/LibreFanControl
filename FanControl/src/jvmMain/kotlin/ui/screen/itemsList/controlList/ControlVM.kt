package ui.screen.itemsList.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.sync.Mutex
import logicControl.isControlChange
import model.item.behavior.Behavior
import model.item.control.Control
import utils.Name.Companion.checkNameTaken

class ControlVM(
    val controlList: SnapshotStateList<Control> = State.hControls,
    val behaviorList: SnapshotStateList<Behavior> = State.iBehaviors,
    val controlChangeList: SnapshotStateList<Boolean> = State.controlChangeList,
    private val mutex: Mutex = State.controlChangeMutex
) {

    fun addControl(index: Int) {
        controlList[index] = controlList[index].copy(
            visible = true
        )
    }

    fun remove(index: Int) {
        updateSafely(index) {
            controlList[index] = controlList[index].copy(
                isAuto = true,
                visible = false
            )
        }
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        updateSafely(index) {
            controlList[index] = controlList[index].copy(
                behaviorId = behaviorId
            )
        }
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        updateSafely(index) {
            controlList[index] = controlList[index].copy(
                isAuto = !checked
            )
        }
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = controlList.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        controlList[index] = controlList[index].copy(
            name = name
        )
    }

    private fun updateSafely(index: Int, operation: () -> Unit) {
        if (!mutex.tryLock())
            return

        if (controlChangeList[index]) {
            mutex.unlock()
            return
        }

        val previousControl = controlList[index].copy()
        operation()
        if (isControlChange(previousControl, controlList[index]))
            controlChangeList[index] = true

        mutex.unlock()
    }
}