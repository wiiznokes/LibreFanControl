package ui.screen.body.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.checkNameTaken

class BehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _controlsChange: MutableStateFlow<Boolean> = State._controlsChange
) {
    private val controlItemList = _controlItemList.asStateFlow()
    private val controlChange = _controlsChange.asStateFlow()

    val behaviorItemList = _behaviorItemList.asStateFlow()


    fun remove(index: Int) {
        val idRemoved = behaviorItemList.value[index].itemId

        // update control if it was linked with this behavior
        controlItemList.value.indexOfFirst { control ->
            control.behaviorId == idRemoved
        }.let { controlIndex ->
            if (controlIndex != -1) {
                if (controlChange.value)
                    return
                _controlItemList.update {
                    println("controlChange = true, behavior ViewModel, remove")
                    _controlsChange.value = true
                    it[controlIndex].behaviorId = null
                    it
                }
            }
        }

        _behaviorItemList.update {
            it.removeAt(index)
            it
        }
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = behaviorItemList.value.map { item ->
                item.name
            },
            name = name,
            index = index
        )

        _behaviorItemList.update {
            it[index] = it[index].copy(
                name = name
            )
            it
        }
    }
}