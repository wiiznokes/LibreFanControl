package ui.screen

import State
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
) {


    val fanList = State._fanList.asStateFlow()
    val tempList = State._tempList.asStateFlow()
    val controlList = State._controlList.asStateFlow()
    val behaviorList = State._behaviorList.asStateFlow()

    val addFanList = State._addFanList.asStateFlow()
    val addTempList = State._addTempList.asStateFlow()
    val addControlList = State._addControlList.asStateFlow()
    val addBehaviorList = State._addBehaviorList.asStateFlow()
}