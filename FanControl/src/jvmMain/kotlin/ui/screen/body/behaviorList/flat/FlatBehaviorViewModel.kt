package ui.screen.body.behaviorList.flat

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.item.behavior.BehaviorItem

class FlatBehaviorViewModel(
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList
) {
    fun onMore(index: Int, value: Int) {
        if (value >= 100) return
        _behaviorItemList.update {
            it[index] = it[index].copy(
                flatBehavior = it[index].flatBehavior?.copy(
                    value = value + 1
                )
            )
            it
        }
    }

    fun onLess(index: Int, value: Int) {
        if (value <= 0) return
        _behaviorItemList.update {
            it[index] = it[index].copy(
                flatBehavior = it[index].flatBehavior?.copy(
                    value = value - 1
                )
            )
            it
        }
    }

    fun onChange(index: Int, value: Int) {
        _behaviorItemList.update {
            it[index] = it[index].copy(
                flatBehavior = it[index].flatBehavior?.copy(
                    value = value
                )
            )
            it
        }
    }
}