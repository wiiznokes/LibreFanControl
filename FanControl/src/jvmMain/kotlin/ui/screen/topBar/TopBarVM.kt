package ui.screen.topBar

import State
import kotlinx.coroutines.flow.MutableStateFlow

class TopBarVM(
    private val editModeActivated: MutableStateFlow<Boolean> = State.editModeActivated,
    private val addItemExpanded: MutableStateFlow<Boolean> = State.addItemExpanded,
) {

    fun edit() {
        editModeActivated.value = !editModeActivated.value
    }

    fun closeAddItem() {
        addItemExpanded.value = false
    }

}