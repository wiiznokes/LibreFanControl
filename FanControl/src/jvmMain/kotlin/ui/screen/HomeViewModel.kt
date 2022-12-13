package ui.screen

import State
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    _editModeActivated: MutableStateFlow<MutableState<Boolean>> = State._editModeActivated,
    _addItemExpanded: MutableStateFlow<MutableState<Boolean>> = State._addItemExpanded,
) {

    val editModeActivated = _editModeActivated.asStateFlow()

    val addItemExpanded = _addItemExpanded.asStateFlow()
}