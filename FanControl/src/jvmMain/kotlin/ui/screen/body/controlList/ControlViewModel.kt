package ui.screen.body.controlList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.checkNameTaken

class ControlViewModel(
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    val behaviorItemList: StateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList.asStateFlow()
) {
    val controlItemList = _controlItemList.asStateFlow()

    fun remove(index: Int, control: ControlItem) {
        _controlItemList.update {
            if (!control.isAuto) {
                it[index].controlShouldStop = true
                it[index].controlShouldBeSet = false
            }
            it[index].visible = false
            it
        }
    }

    fun setBehavior(index: Int, behaviorId: Long?, control: ControlItem) {
        _controlItemList.update {
            // we change only if needed
            if (control.behaviorId != behaviorId) {
                it[index].behaviorId = behaviorId

                if (behaviorId == null) {
                    it[index].controlShouldStop = true
                    it[index].controlShouldBeSet = false
                }
                else {
                    it[index].controlShouldStop = false
                    it[index].controlShouldBeSet = true
                }
            }
            it
        }
    }

    fun onSwitchClick(checked: Boolean, index: Int) {
        _controlItemList.update {
            it[index].controlShouldStop = !checked
            it[index].controlShouldBeSet = checked
            it
        }
    }


    fun setName(name: String, index: Int) {
        checkNameTaken(
            names = _controlItemList.value.map { item ->
                item.name
            },
            name = name,
            index = index
        )
        _controlItemList.update {
            it[index] = it[index].copy(
                name = name
            )
            it
        }
    }
}