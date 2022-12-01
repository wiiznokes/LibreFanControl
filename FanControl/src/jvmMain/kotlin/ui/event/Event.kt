package ui.event

import configuration.ConfigurationModel
import model.BaseItem
import model.hardware.control.FanControl
import ui.ItemType

open class Event {

    open class Initialisation {
        object FetchHardware : Event()
        object Stop : Event()
    }

    open class Organisation {
        data class Add(val type: ItemType, val id: String): Event()
        data class Remove(val type: ItemType, val id: String): Event()
    }

    open class Configuration {
        data class Save(val confId: String): Event()
        data class Remove(val confId: String): Event()

        data class Apply(val configId: String): Event()
    }



    open class Item {
        data class SetName(val item: BaseItem, val name: String) : Event()

        open class Control {
            data class SetValue(val id: String, val isAuto: Boolean, val value: Int) : Event()
            data class SetBehaviorId(val index: Int, val behaviorId: String): Event()
        }
    }
}

