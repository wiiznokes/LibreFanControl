package ui.screen.body.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.BehaviorItem

class BehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    val behaviorItemList = State._behaviorItemList.asStateFlow()


    fun remove(index: Int) {
        _behaviorItemList.update {
            it.removeAt(index)
            it
        }
    }


    fun onMore(index: Int) {
        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                value = _behaviorItemList.value[index].value + 1
            )
            it
        }
    }

    fun onLess(index: Int) {
        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                value = _behaviorItemList.value[index].value - 1
            )
            it
        }
    }

    fun onChange(index: Int, value: Int) {
        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                value = value
            )
            it
        }
    }


    fun setName(name: String, index: Int) {

        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                name = name
            )
            it
        }
    }
}