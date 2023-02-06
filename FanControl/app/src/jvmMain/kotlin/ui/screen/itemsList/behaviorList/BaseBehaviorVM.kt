package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.item.behavior.BaseIBehavior
import model.item.control.IControl
import utils.Name.Companion.checkNameTaken
import utils.getIndexList

open class BaseBehaviorVM(
    val iBehaviors: SnapshotStateList<BaseIBehavior> = State.iBehaviors,
    private val iControls: SnapshotStateList<IControl> = State.iControls
) {
    fun remove(index: Int) {
        val behavior = iBehaviors[index]

        val indexList = getIndexList(
            list = iControls,
            predicate = { it.behaviorId.value == behavior.id }
        )

        indexList.forEach {
            iControls[it].behaviorId.value = null
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