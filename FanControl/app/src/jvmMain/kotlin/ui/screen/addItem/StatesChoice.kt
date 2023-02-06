package ui.screen.addItem

import ui.utils.Resources

enum class ChoiceType {
    CONTROL,
    BEHAVIOR,
    SENSOR
}

private class ChoiceStateException(msg: String = "") : Exception(msg)

data class ChoiceState(
    val current: ChoiceType = ChoiceType.BEHAVIOR,
    val animationSign: Int = 0,
    val title: String = Resources.getString("title/behavior"),
    val previous: ChoiceType = ChoiceType.CONTROL,
    val next: ChoiceType = ChoiceType.SENSOR,
)


fun updateChoiceState(
    state: ChoiceState,
): ChoiceState {
    val currentState = when (state.animationSign) {
        -1 -> state.previous
        1 -> state.next
        else -> throw ChoiceStateException()
    }

    return when (currentState) {
        ChoiceType.CONTROL -> controlChoice(state)
        ChoiceType.BEHAVIOR -> behaviorChoice(state)
        ChoiceType.SENSOR -> sensorChoice(state)
    }
}


private fun behaviorChoice(
    state: ChoiceState,
): ChoiceState = state.copy(
    title = Resources.getString("title/behavior"),
    current = ChoiceType.BEHAVIOR,
    previous = ChoiceType.CONTROL,
    next = ChoiceType.SENSOR
)

private fun controlChoice(
    state: ChoiceState,
): ChoiceState = state.copy(
    title = Resources.getString("title/control"),
    current = ChoiceType.CONTROL,
    previous = ChoiceType.SENSOR,
    next = ChoiceType.BEHAVIOR
)

private fun sensorChoice(
    state: ChoiceState,
): ChoiceState = state.copy(
    title = Resources.getString("title/sensor"),
    current = ChoiceType.SENSOR,
    previous = ChoiceType.BEHAVIOR,
    next = ChoiceType.CONTROL
)