package ui.screen.addItem

import ui.utils.Resources


interface IChoiceStates {
    val title: String
    val next: ChoiceType
    val previous: ChoiceType
}

enum class ChoiceType {
    BEHAVIOR,
    SENSOR,
    CONTROL
}

class ChoiceStates {
    private val behaviorChoice = BehaviorChoice()
    private val sensorChoice = SensorChoice()
    private val controlChoice = ControlChoice()

    fun getState(choiceType: ChoiceType): IChoiceStates {
        return when (choiceType) {
            ChoiceType.BEHAVIOR -> {
                behaviorChoice
            }

            ChoiceType.SENSOR -> {
                sensorChoice
            }

            ChoiceType.CONTROL -> {
                controlChoice
            }
        }
    }
}

class BehaviorChoice : IChoiceStates {

    override val title: String = Resources.getString("add_item_choice_behavior")
    override val next: ChoiceType = ChoiceType.SENSOR
    override val previous: ChoiceType = ChoiceType.CONTROL
}


class SensorChoice : IChoiceStates {
    override val title: String = Resources.getString("add_item_choice_sensor")
    override val next: ChoiceType = ChoiceType.CONTROL
    override val previous: ChoiceType = ChoiceType.BEHAVIOR
}

class ControlChoice : IChoiceStates {
    override val title: String = Resources.getString("add_item_choice_control")
    override val next: ChoiceType = ChoiceType.BEHAVIOR
    override val previous: ChoiceType = ChoiceType.SENSOR
}