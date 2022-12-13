package ui.screen.body.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.behavior.BehaviorItem

class BehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {

    val behaviorItemList = _behaviorItemList.asStateFlow()


    fun remove(index: Int) {
        _behaviorItemList.update {
            it.removeAt(index)
            it
        }
    }


    fun setName(name: String, index: Int) {
        if (_behaviorItemList.value.count {
                it.name == name
            } != 0
        ) throw IllegalArgumentException()

        _behaviorItemList.update {
            _behaviorItemList.value[index] = _behaviorItemList.value[index].copy(
                name = name
            )
            it
        }
    }
}