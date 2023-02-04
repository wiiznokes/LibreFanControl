package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.behavior.Behavior
import model.item.control.ControlItem
import utils.Name.Companion.checkNameTaken
import utils.getIndexList

open class BaseBehaviorVM(
    val iBehaviors: SnapshotStateList<Behavior> = State.iBehaviors,
    private val iControls: SnapshotStateList<ControlItem> = State.iControls
) {
    fun remove(index: Int) {
        val behavior = iBehaviors[index]

        val indexList = getIndexList(
            list = iControls,
            predicate = { it.behaviorId == behavior.id }
        )

        indexList.forEach {
            iControls[it] = iControls[it].copy(behaviorId = null)
        }
        iBehaviors.removeAt(index)
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = iBehaviors.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        iBehaviors[index] = iBehaviors[index].copy(
            name = name
        )
    }


    fun addBehavior(behavior: Behavior) {
        iBehaviors.add(behavior)
    }
}