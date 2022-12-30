package logicControl

import external.SetControlModel
import model.ItemType
import model.UnspecifiedTypeException
import model.hardware.Sensor
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior


fun getSetControlList(
    controlItemList: List<ControlItem>,
    behaviorItemList: List<BehaviorItem>,
    tempList: List<Sensor>
): List<SetControlModel> {

    val finalSetControlList = mutableListOf<SetControlModel>()

    handleControlShouldStop(finalSetControlList, controlItemList)

    handleControlShouldBeSet(
        setControlList = finalSetControlList,
        controlItemList = controlItemList,
        behaviorItemList = behaviorItemList,
        tempList = tempList
    )

    return finalSetControlList
}


private fun handleControlShouldStop (
    setControlList: MutableList<SetControlModel>,
    controlItemList: List<ControlItem>
) {
    val previousIndexList = mutableListOf<Int>()

    controlItemList.filterIndexed { index, control ->
        if (control.controlShouldStop) {
            previousIndexList.add(index)
            true
        }
        else false
    }.forEachIndexed { index, control ->
        setControlList.add(
            SetControlModel(
                libIndex = control.libIndex,
                isAuto = true,
                index = previousIndexList[index],
                controlShouldBeSet = false
            )
        )
    }
}


private fun handleControlShouldBeSet(
    setControlList: MutableList<SetControlModel>,
    controlItemList: List<ControlItem>,
    behaviorItemList: List<BehaviorItem>,
    tempList: List<Sensor>
) {
    val previousIndexList = mutableListOf<Int>()

    controlItemList.filterIndexed { index, control ->
        if (control.controlShouldBeSet) {
            previousIndexList.add(index)
            true
        }
        else false
    }.forEachIndexed label@{ index, control ->
        val behavior = behaviorItemList.find { behavior ->
            behavior.itemId == control.behaviorId
        }

        var controlShouldBeSet = true

        val value = when (behavior!!.type) {
            ItemType.BehaviorType.I_B_FLAT -> {
                controlShouldBeSet = false
                (behavior.extension as FlatBehavior).value
            }

            ItemType.BehaviorType.I_B_LINEAR ->
                when (val res = valueLinear(behavior.extension as LinearBehavior, tempList)) {
                    null ->
                        // continue keyword of forEach loop
                        // we only return from the lambda expression
                        // (control here)
                        return@label

                    else -> res
                }

            ItemType.BehaviorType.I_B_TARGET -> TODO()
            else -> throw UnspecifiedTypeException()
        }

        setControlList.add(
            SetControlModel(
                libIndex = control.libIndex,
                isAuto = false,
                value = value,
                index = previousIndexList[index],
                controlShouldBeSet = controlShouldBeSet
            )
        )
    }
}
