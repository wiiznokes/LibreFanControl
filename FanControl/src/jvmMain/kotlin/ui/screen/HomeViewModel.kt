package ui.screen

import State
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {


    val fanList = State._fanList.asStateFlow()
    val tempList = State._tempList.asStateFlow()
    val controlList = State._controlList.asStateFlow()
    val behaviorList = State._behaviorList.asStateFlow()

    val addFanList = State._addFanList.asStateFlow()
    val addTempList = State._addTempList.asStateFlow()
    val addControlList = State._addControlList.asStateFlow()
    val addBehaviorList = State._addBehaviorList.asStateFlow()

    @OptIn(ExperimentalMaterial3Api::class)
    val drawerState = State._drawerState.asStateFlow()
    val addItemExpanded = State._addItemExpanded.asStateFlow()

    val editModeActivated = State._editModeActivated.asStateFlow()
}