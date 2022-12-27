package ui.screen.addItem.control

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.ControlItem

class AddControlViewModel(
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
) {
    val controlItemList = _controlItemList.asStateFlow()

    fun addControl(index: Int) {
        _controlItemList.update {
            it[index] = it[index].copy(
                visible = true
            )
            it
        }
    }
}