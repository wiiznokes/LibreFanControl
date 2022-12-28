package ui.screen.body.controlList

import Application
import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import utils.checkNameTaken

class ControlViewModel(
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
) {
    val controlItemList = _controlItemList.asStateFlow()
    val behaviorItemList = _behaviorItemList.asStateFlow()


    fun remove(index: Int, libIndex: Int) {
        Application.setControl(
            libIndex = libIndex,
            isAuto = false
        )

        _controlItemList.update {
            it[index] = it[index].copy(
                visible = false,
                isActive = false
            )
            it
        }
    }

    fun setBehavior(index: Int, behaviorId: Long?) {
        _controlItemList.update {
            it[index] = it[index].copy(
                behaviorId = behaviorId
            )
            it
        }
    }

    fun setControl(libIndex: Int, isAuto: Boolean) {
        // we set control only if is Auto is false because
        // another class is in charge to know if control should be set
        if (!isAuto) {
            Application.setControl(
                libIndex = libIndex,
                isAuto = false
            )
        }

        _controlItemList.update {
            it[libIndex] = it[libIndex].copy(
                isActive = isAuto
            )
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