package ui.screen.body.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.checkNameTaken
import utils.filterWithPreviousIndex

class BehaviorViewModel(
    val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
    private val controlItemList: SnapshotStateList<ControlItem> = State.controlItemList,
    private val controlsChange: MutableStateFlow<Boolean> = State.controlsChange
) {

    fun remove(index: Int) {
        if (controlsChange.value)
            return

        val idRemoved = behaviorItemList[index].itemId

        var controlHasChangeMarker = false
        // update control if it was linked with this behavior
        filterWithPreviousIndex(
            list = controlItemList,
            predicate = {
                it.behaviorId == idRemoved
            }
        ) { controlIndex, _ ->
            controlItemList[controlIndex] = controlItemList[controlIndex].copy(
                behaviorId = null
            )
            controlHasChangeMarker = true
        }
        if (controlHasChangeMarker)
            controlsChange.value = true
        behaviorItemList.removeAt(index)
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = behaviorItemList.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        behaviorItemList[index] = behaviorItemList[index].copy(
            name = name
        )
    }
}