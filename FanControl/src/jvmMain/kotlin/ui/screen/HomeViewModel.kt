package ui.screen

import State
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val _editModeActivated: MutableStateFlow<MutableState<Boolean>> = State._editModeActivated,
    @OptIn(ExperimentalMaterial3Api::class)
    private val _drawerState: MutableStateFlow<DrawerState> = State._drawerState,
    private val _addItemExpanded: MutableStateFlow<MutableState<Boolean>> = State._addItemExpanded,
) {

    val editModeActivated = _editModeActivated.asStateFlow()

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState = _drawerState.asStateFlow()

    val addItemExpanded = _addItemExpanded.asStateFlow()
}