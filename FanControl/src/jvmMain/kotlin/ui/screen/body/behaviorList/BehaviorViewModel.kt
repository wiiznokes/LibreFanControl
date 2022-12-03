package ui.screen.body.behaviorList

import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.behavior.Behavior

class BehaviorViewModel(
    private val _behaviorList: MutableStateFlow<SnapshotStateList<Behavior>>,
    private val _addBehaviorList: MutableStateFlow<SnapshotStateList<Behavior>>
) {
    fun remove(index: Int) {
        _behaviorList.update {
            val tempBehavior = it.removeAt(index)
            _addBehaviorList.update { it2 ->
                _addBehaviorList.value.add(tempBehavior)
                it2
            }
            it
        }
    }


    fun setName(name: String, index: Int) {

        _behaviorList.update {
            _behaviorList.value[index] = _behaviorList.value[index].copy(
                name = name
            )
            it
        }
    }
}