package ui.screen.itemsList.behaviorList.flat

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.behavior.Behavior
import model.item.behavior.Flat

class FlatVM(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList
) {
    fun onMore(index: Int, value: Int) {
        if (value >= 100) return

        behaviorList[index] = behaviorList[index].copy(
            extension = (behaviorList[index].extension as Flat).copy(
                value = value + 1
            )
        )
    }

    fun onLess(index: Int, value: Int) {
        if (value <= 0) return

        behaviorList[index] = behaviorList[index].copy(
            extension = (behaviorList[index].extension as Flat).copy(
                value = value - 1
            )
        )
    }

    fun onValueChange(index: Int, value: Int) {
        behaviorList[index] = behaviorList[index].copy(
            extension = (behaviorList[index].extension as Flat).copy(
                value = value
            )
        )
    }
}