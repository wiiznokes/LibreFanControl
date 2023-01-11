package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.sync.Mutex
import logicControl.isControlChange
import model.item.behavior.Behavior
import model.item.control.Control
import utils.Name.Companion.checkNameTaken
import utils.getIndexList

class BodyBehaviorVM(
    val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    private val controlList: SnapshotStateList<Control> = State.controlList,
    private val controlChangeList: SnapshotStateList<Boolean> = State.controlChangeList,
    private val mutex: Mutex = State.controlChangeMutex
) {

    fun updateSafely(
        index: Int,
        behaviorOperation: () -> Unit,
        controlOperation: ((Int) -> Unit)? = null,
        controlShouldChange: ((Int, Control) -> Boolean)? = null
    ) {
        val behavior = behaviorList[index]

        val indexList = getIndexList(
            list = controlList,
            predicate = { it.behaviorId == behavior.id }
        )

        if (indexList.isEmpty()) {
            behaviorOperation()
            return
        }

        if (!mutex.tryLock())
            return

        behaviorOperation()

        for (controlIndex in indexList) {
            val previousControl = controlList[controlIndex]
            controlOperation?.invoke(controlIndex)
            val shouldChange = when (controlShouldChange) {
                null -> true
                else -> controlShouldChange(controlIndex, previousControl)
            }
            if (shouldChange)
                controlChangeList[controlIndex] = true
        }

        mutex.unlock()
    }

    fun remove(index: Int) {
        updateSafely(
            index = index,
            behaviorOperation = { behaviorList.removeAt(index) },
            controlOperation = { controlList[it] = controlList[it].copy(behaviorId = null) },
            controlShouldChange = { controlIndex, previousControl ->
                isControlChange(previousControl, controlList[controlIndex])
            }
        )
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