package ui.screen.addItem.behavior

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.behavior.Behavior

class AddBehaviorViewModel(
    private val _behaviorList: MutableStateFlow<SnapshotStateList<Behavior>> = State._behaviorList,
    private val _addBehaviorList: MutableStateFlow<SnapshotStateList<Behavior>> = State._addBehaviorList
) {

    fun add(index: Int) {
        _addBehaviorList.update {
            val tempBehavior = it.removeAt(index)
            _behaviorList.update { it2 ->
                _behaviorList.value.add(tempBehavior)
                it2
            }
            it
        }
    }
}