package ui.screen

import State
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val _editModeActivated: MutableStateFlow<MutableState<Boolean>> = State._editModeActivated
) {

    val editModeActivated = _editModeActivated.asStateFlow()

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState = State._drawerState.asStateFlow()
    val addItemExpanded = State._addItemExpanded.asStateFlow()
}