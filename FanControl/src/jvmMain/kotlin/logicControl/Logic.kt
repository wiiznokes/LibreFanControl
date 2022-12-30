package logicControl


import kotlinx.coroutines.flow.asStateFlow
import model.ItemType
import model.UnspecifiedTypeException
import model.hardware.Sensor
import model.item.ControlItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior


data class SetControlModel(
    val libIndex: Int?,
    val isAuto: Boolean,
    val value: Int? = null,
    val index: Int,
    val controlShouldBeSet: Boolean
)

fun getSetControlList(
    controlItemList: List<ControlItem>,
    behaviorItemList: List<BehaviorItem>,
    tempList: List<Sensor>
): List<SetControlModel> {

    val finalSetControlList = mutableListOf<SetControlModel>()


    handleHasNotVerify(
        setControlList = finalSetControlList,
        controlItemList = controlItemList
    )

    handleControlShouldBeSet(
        setControlList = finalSetControlList,
        controlItemList = controlItemList,
        behaviorItemList = behaviorItemList,
        tempList = tempList
    )

    return finalSetControlList
}

/*

*/
private fun handleHasNotVerify(
    setControlList: MutableList<SetControlModel>,
    controlItemList: List<ControlItem>
) {
    baseHandle(
        controlItemList = controlItemList,
        predicate = { !it.logicHasVerify }
    ) { index, control ->

        // case if control have to change to auto
        if (control.isAuto || control.behaviorId == null) {
            setControlList.add(
                SetControlModel(
                    libIndex = control.libIndex,
                    isAuto = true,
                    index = index,
                    controlShouldBeSet = false
                )
            )
        } else {
            SetControlModel(
                libIndex = null,
                isAuto = true,
                index = index,
                controlShouldBeSet = true
            )
        }
    }
}

private fun handleControlShouldBeSet(
    setControlList: MutableList<SetControlModel>,
    controlItemList: List<ControlItem>,
    behaviorItemList: List<BehaviorItem>,
    tempList: List<Sensor>
) {

    baseHandle(
        controlItemList = controlItemList,
        predicate = { !it.logicHasVerify && it.controlShouldBeSet }
    ) label@{ index, control ->
        val behavior = behaviorItemList.find { behavior ->
            behavior.itemId == control.behaviorId
        }
        println(behavior)

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
                isAuto = true,
                value = value,
                index = index,
                controlShouldBeSet = controlShouldBeSet
            )
        )
    }
}


private fun baseHandle(
    controlItemList: List<ControlItem>,
    predicate: (ControlItem) -> Boolean,
    forEachFiltered: (Int, ControlItem) -> Unit
) {
    val previousIndexList = mutableListOf<Int>()

    val list = controlItemList.filterIndexed { index, control ->
        if (predicate(control)) {
            previousIndexList.add(index)
            true
        } else false
    }

    //println("between")

    list.forEachIndexed { index, control ->
        forEachFiltered(previousIndexList[index], control)
    }
}