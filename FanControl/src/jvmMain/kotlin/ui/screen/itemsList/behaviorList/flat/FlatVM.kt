package ui.screen.itemsList.behaviorList.flat

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.sync.Mutex
import model.item.behavior.Behavior
import model.item.behavior.Flat
import model.item.control.Control

class FlatVM(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    val controlList: SnapshotStateList<Control> = State.controlList,
    val controlChangeList: SnapshotStateList<Boolean> = State.controlChangeList,
    private val mutex: Mutex = State.controlChangeMutex
) {

    private fun updateSafely(index: Int, value: Int) {
        val behavior = behaviorList[index]

        val controlIndex = controlList.indexOfFirst {
            it.behaviorId == behavior.id
        }
        if (controlIndex == -1) {
            behaviorList[index] = behavior.copy(
                extension = (behavior.extension as Flat).copy(
                    value = value
                )
            )
            return
        }

        if (!mutex.tryLock())
            return

        behaviorList[index] = behavior.copy(
            extension = (behavior.extension as Flat).copy(
                value = value
            )
        )

        controlChangeList[controlIndex] = true
        mutex.unlock()
    }


    fun onMore(index: Int, value: Int) {
        if (value >= 100) return

        updateSafely(index, value + 1)
    }

    fun onLess(index: Int, value: Int) {
        if (value <= 0) return

        updateSafely(index, value - 1)
    }

    fun onValueChange(index: Int, value: Int) {
        updateSafely(index, value)
    }
}