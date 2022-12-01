package ui

open class Event {
    data class SetControl(val id: Int, val isAuto: Boolean, val value: Int) : Event()

    object Reload : Event()

    object Stop : Event()
}