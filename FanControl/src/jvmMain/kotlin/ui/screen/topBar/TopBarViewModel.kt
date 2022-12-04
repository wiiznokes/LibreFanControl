package ui.screen.topBar

import State
import kotlinx.coroutines.flow.update

class TopBarViewModel {

    private val _editModeActivated = State._editModeActivated


    fun save() {

    }

    fun remove() {

    }

    fun edit() {
        _editModeActivated.update { !it }
    }
}