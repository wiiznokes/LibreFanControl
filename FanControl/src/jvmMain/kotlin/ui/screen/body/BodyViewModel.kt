package ui.screen.body

import State
import kotlinx.coroutines.flow.MutableStateFlow

class BodyViewModel(
    val addItemExpanded: MutableStateFlow<Boolean> = State.addItemExpanded
) {
    fun expandAddItem() {
        addItemExpanded.value = true
    }
}