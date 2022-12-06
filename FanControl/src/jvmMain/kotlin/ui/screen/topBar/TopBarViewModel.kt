package ui.screen.topBar

import State
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TopBarViewModel(
    private val _editModeActivated: MutableStateFlow<MutableState<Boolean>> = State._editModeActivated
) {

    val editModeActivated = _editModeActivated.asStateFlow()


    fun save() {

    }

    fun remove() {

    }

    fun edit() {
        _editModeActivated.update {
            it.value = !it.value
            it
        }
    }
}