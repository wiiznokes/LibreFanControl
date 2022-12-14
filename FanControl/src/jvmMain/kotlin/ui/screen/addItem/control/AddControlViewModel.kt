package ui.screen.addItem.control

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Control
import model.item.ControlItem

class AddControlViewModel(
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
) {

    val controlList = _controlList.asStateFlow()
    val controlItemList = _controlItemList.asStateFlow()

    fun addControl(index: Int) {
        _controlItemList.update {
            _controlItemList.value[index] = _controlItemList.value[index].copy(
                visible = true
            )
            it
        }
    }
}