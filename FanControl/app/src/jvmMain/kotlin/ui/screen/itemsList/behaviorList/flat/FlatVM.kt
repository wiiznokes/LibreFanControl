package ui.screen.itemsList.behaviorList.flat

import model.item.BaseI
import model.item.BaseI.Companion.getAvailableId
import model.item.BaseI.Companion.getAvailableName
import model.item.IFlat
import ui.screen.itemsList.behaviorList.BaseBehaviorVM
import ui.utils.Resources

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
        name = getAvailableName(
            list = iBehaviors.map { item ->
                item.name.value
            },
            prefix = Resources.getString("default/flat_name")
        ),
        id = getAvailableId(
            list = iBehaviors.map { item ->
                item.id
            },
            prefix = BaseI.IFlatPrefix
        )
    )
}