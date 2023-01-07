package ui.screen.itemsList.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.item.behavior.Behavior
import model.item.control.Control
import utils.Name.Companion.checkNameTaken
import utils.filterWithPreviousIndex

class BodyBehaviorVM(
    val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    private val controlList: SnapshotStateList<Control> = State.controlList,
    private val controlsChange: MutableStateFlow<Boolean> = State.controlsChange
) {

    fun remove(index: Int) {
        if (controlsChange.value)
            return

        val idRemoved = behaviorList[index].id

        var controlHasChangeMarker = false
        // update control if it was linked with this behavior
        filterWithPreviousIndex(
            list = controlList,
            predicate = {
                it.behaviorId == idRemoved
            }
        ) { controlIndex, _ ->
            controlList[controlIndex] = controlList[controlIndex].copy(
                behaviorId = null
            )
            controlHasChangeMarker = true
        }
        if (controlHasChangeMarker)
            controlsChange.value = true
        behaviorList.removeAt(index)
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