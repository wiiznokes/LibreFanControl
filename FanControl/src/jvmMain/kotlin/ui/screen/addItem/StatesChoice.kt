package ui.screen.addItem

import ui.utils.Resources


interface IChoiceStates {
    val title: String
    val previous: ChoiceType
    val next: ChoiceType
}

enum class ChoiceType {
    CONTROL,
    BEHAVIOR,
    SENSOR
}

class ChoiceStates {
    private val controlChoice = ControlChoice()
    private val behaviorChoice = BehaviorChoice()
    private val sensorChoice = SensorChoice()

    fun getState(choiceType: ChoiceType): IChoiceStates {
        return when (choiceType) {
            ChoiceType.CONTROL -> {
                controlChoice
            }

            ChoiceType.BEHAVIOR -> {
                behaviorChoice
            }

            ChoiceType.SENSOR -> {
                sensorChoice
            }
        }
    }
}

class BehaviorChoice : IChoiceStates {

    override val title: String = Resources.getString("title/behavior")
    override val previous: ChoiceType = ChoiceType.CONTROL
    override val next: ChoiceType = ChoiceType.SENSOR
}


class SensorChoice : IChoiceStates {
    override val title: String = Resources.getString("title/sensor")
    override val previous: ChoiceType = ChoiceType.BEHAVIOR
    override val next: ChoiceType = ChoiceType.CONTROL
}

class ControlChoice : IChoiceStates {
    override val title: String = Resources.getString("title/control")
    override val previous: ChoiceType = ChoiceType.SENSOR
    override val next: ChoiceType = ChoiceType.BEHAVIOR
}