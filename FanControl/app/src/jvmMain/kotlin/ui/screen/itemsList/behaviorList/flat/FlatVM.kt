package ui.screen.itemsList.behaviorList.flat

import model.ItemType
import model.item.behavior.IFlat
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.utils.Resources
import utils.Id
import utils.Name

class FlatVM : BaseBehaviorVM() {

    private fun updateValue(index: Int, value: Int) {
        iBehaviors[index].value.value = value
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


    fun defaultFlat() = IFlat(
        name = Name.getAvailableName(
            names = iBehaviors.map { item ->
                item.name.value
            },
            prefix = Resources.getString("default/flat_name")
        ),
        id = Id.getAvailableId(
            ids = iBehaviors.map { item ->
                item.id
            },
            prefix = "flat"
        )
    )
}