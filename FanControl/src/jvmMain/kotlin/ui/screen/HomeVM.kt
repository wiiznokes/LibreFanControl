package ui.screen

import State
import kotlinx.coroutines.flow.MutableStateFlow

class HomeVM(
    val addItemExpanded: MutableStateFlow<Boolean> = State.addItemExpanded
)