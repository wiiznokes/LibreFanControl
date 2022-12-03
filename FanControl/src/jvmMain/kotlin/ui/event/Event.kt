package ui.event

open class Event {

    open class Initialisation {
        object FetchHardware : Event()
        object Stop : Event()
    }

    open class Organisation {
        data class Add(val globalItemType: String, val index: Int) : Event()
        data class Remove(val globalItemType: String, val index: Int) : Event()
    }

    open class Configuration {
        data class Save(val confId: String) : Event()
        data class Remove(val confId: String) : Event()

        data class Apply(val configId: String) : Event()
    }


    open class Item {
        data class SetName(val name: String, val globalItemType: String, val index: Int, val source: Source? = null) :
            Event()

        data class SetExpanded(
            val expanded: Boolean,
            val globalItemType: String,
            val index: Int,
            val source: Source? = null
        ) :
            Event()

        open class Control {
            data class SetValue(val index: Int, val isAuto: Boolean, val value: Int) : Event()
            data class SetBehaviorId(val index: Int, val behaviorId: String) : Event()
        }
    }
}

