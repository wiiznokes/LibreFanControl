package ui.screen

import State
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel(
    val addItemExpanded: MutableStateFlow<Boolean> = State.addItemExpanded
)