package ui.screen.itemsList.behaviorList.flat

import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Flat
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.utils.Resources
import utils.Id
import utils.Name

class FlatVM : BaseBehaviorVM() {

    private fun updateValue(index: Int, value: Int) {
        behaviorList[index] = behaviorList[index].copy(
            extension = (behaviorList[index].extension as Flat).copy(
                value = value
            )
        )
    }

    fun onMore(index: Int, value: Int) {
        if (value >= 100) return
        updateSafely(
            index = index,
            behaviorOperation = {
                updateValue(index, value + 1)
            }
        )
    }

    fun onLess(index: Int, value: Int) {
        if (value <= 0) return

        updateSafely(
            index = index,
            behaviorOperation = {
                updateValue(index, value - 1)
            }
        )
    }

    fun onValueChange(index: Int, value: Int) {
        updateSafely(
            index = index,
            behaviorOperation = {
                updateValue(index, value)
            }
        )
    }


    fun defaultFlat() = Behavior(
        name = Name.getAvailableName(
            names = behaviorList.map { item ->
                item.name
            },
            prefix = Resources.getString("default/flat_name")
        ),
        type = ItemType.BehaviorType.I_B_FLAT,
        extension = Flat(),
        id = Id.getAvailableId(
            ids = behaviorList.map { item ->
                item.id
            }
        )
    )
}