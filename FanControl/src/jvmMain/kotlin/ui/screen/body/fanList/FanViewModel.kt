package ui.screen.body.fanList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.hardware.Sensor
import model.item.LibItem

class FanViewModel(
    private val _fanItemList: MutableStateFlow<SnapshotStateList<LibItem>> = State._fanItemList,
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList
) {
    val fanList = State._fanList.asStateFlow()
    val fanItemList = State._fanItemList.asStateFlow()

    fun remove(index: Int) {
        _fanItemList.update {
            _fanItemList.value.removeAt(index)
            it
        }
    }

    fun changeSensorId(index: Int, menuIndex: Int) {
        _fanItemList.update {
            _fanItemList.value[index] = _fanItemList.value[index].copy(
                sensorName = _fanList.value[menuIndex].libName
            )
            it
        }
    }

    fun setName(name: String, index: Int) {
        _fanItemList.update {
            _fanItemList.value[index] = _fanItemList.value[index].copy(
                name = name
            )
            it
        }
    }
}