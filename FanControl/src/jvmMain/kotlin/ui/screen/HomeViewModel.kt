package ui.screen

import State
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState = State._drawerState.asStateFlow()
    val addItemExpanded = State._addItemExpanded.asStateFlow()

    val editModeActivated = State._editModeActivated.asStateFlow()
}