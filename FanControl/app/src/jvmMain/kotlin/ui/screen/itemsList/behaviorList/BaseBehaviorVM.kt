package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.BaseI.Companion.checkNameTaken
import model.item.BaseIBehavior
import model.item.IControl
import utils.getIndexList

open class BaseBehaviorVM(
    val iBehaviors: SnapshotStateList<BaseIBehavior> = State.iBehaviors,
    private val iControls: SnapshotStateList<IControl> = State.iControls,
) {
    fun remove(index: Int) {
        val behavior = iBehaviors[index]

        val indexList = getIndexList(
            list = iControls,
            predicate = { it.iBehaviorId.value == behavior.id }
        )

        indexList.forEach {
            iControls[it].iBehaviorId.value = null
        }
        iBehaviors.removeAt(index)
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = iBehaviors.map { item ->
                item.name.value
            },
            name = name,
            index = index
        )
        iBehaviors[index].name.value = name
    }


    fun addBehavior(behavior: BaseIBehavior) {
        iBehaviors.add(behavior)
    }
}