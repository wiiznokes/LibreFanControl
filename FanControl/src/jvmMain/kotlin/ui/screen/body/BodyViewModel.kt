package ui.screen.body

import State
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class BodyViewModel(
    private val _addItemExpanded: MutableStateFlow<MutableState<Boolean>> = State._addItemExpanded,
) {
    fun expandAddItem() {
        _addItemExpanded.update {
            it.value = true
            it
        }
    }
}