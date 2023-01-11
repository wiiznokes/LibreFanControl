package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.sync.Mutex
import model.item.behavior.Behavior
import model.item.control.Control
import utils.Name.Companion.checkNameTaken

class BodyBehaviorVM(
    val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    private val controlList: SnapshotStateList<Control> = State.controlList,
    private val controlChangeList: SnapshotStateList<Boolean> = State.controlChangeList,
    private val mutex: Mutex = State.controlChangeMutex
) {

    fun remove(index: Int) {
        val behavior = behaviorList[index]

        val controlIndex = controlList.indexOfFirst {
            it.behaviorId == behavior.id
        }
        if (controlIndex == -1) {
            behaviorList.removeAt(index)
            return
        }

        if (!mutex.tryLock())
            return

        controlList[controlIndex] = controlList[controlIndex].copy(
            behaviorId = null
        )
        controlChangeList[controlIndex] = true
        mutex.unlock()
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = behaviorList.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        behaviorList[index] = behaviorList[index].copy(
            name = name
        )
    }
}