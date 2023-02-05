package ui.screen.itemsList.behaviorList.flat

import external.proto.conf.IsensorType
import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Flat
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.utils.Resources
import utils.Id
import utils.Name

class FlatVM : BaseBehaviorVM() {

    private fun updateValue(index: Int, value: Int) {
        iBehaviors[index] = iBehaviors[index].copy(
            extension = (iBehaviors[index].extension as Flat).copy(
                value = value
            )
        )
    }

    fun onMore(index: Int, value: Int) {
        if (value >= 100) return

        updateValue(index, value + 1)

    }

    fun onLess(index: Int, value: Int) {
        if (value <= 0) return


        updateValue(index, value - 1)


    }

    fun onValueChange(index: Int, value: Int) {

        updateValue(index, value)


    }


    fun defaultFlat() = Behavior(
        name = Name.getAvailableName(
            names = iBehaviors.map { item ->
                item.name
            },
            prefix = Resources.getString("default/flat_name")
        ),
        type = ItemType.BehaviorType.I_B_FLAT,
        extension = Flat(),
        id = Id.getAvailableId(
            ids = iBehaviors.map { item ->
                item.id
            }
        )
    )
}