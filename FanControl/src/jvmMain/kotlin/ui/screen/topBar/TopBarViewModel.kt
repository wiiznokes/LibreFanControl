package ui.screen.topBar

import State
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TopBarViewModel(
    private val _editModeActivated: MutableStateFlow<MutableState<Boolean>> = State._editModeActivated,
    private val _addItemExpanded: MutableStateFlow<MutableState<Boolean>> = State._addItemExpanded,
) {

    fun edit() {
        _editModeActivated.update {
            it.value = !it.value
            it
        }
    }

    fun unexpandAddItem() {
        _addItemExpanded.update {
            it.value = false
            it
        }
    }

}