package ui.screen.body.behaviorList

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.item.behavior.BehaviorItem
import ui.utils.checkNameTaken

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
        checkNameTaken(
            names = behaviorItemList.value.map { item ->
                item.name
            },
            name = name,
            index = index
        )

        _behaviorItemList.update {
            it[index] = it[index].copy(
                name = name
            )
            it
        }
    }
}