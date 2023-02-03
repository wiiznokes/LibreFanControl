package logicControl.uiUpdate

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import logicControl.behavior.BehaviorLogic
import logicControl.temp.TempLogic
import model.ItemType
import model.item.behavior.Behavior
import model.item.sensor.CustomTemp
import model.item.sensor.SensorItem
import utils.filterWithPreviousIndex


/**
 * class used to update behavior and temp on the screen
 * Should be used only if app is visible and in a coroutine
 */
class UiUpdate(
    private val behaviorList: SnapshotStateList<Behavior> = State.iBehaviors,
    private val tempItemList: SnapshotStateList<SensorItem> = State.iTemps
) {

    private val behaviorLogic = BehaviorLogic()

    fun update() {
        try {
            for (i in behaviorList.indices) {
                val behavior = behaviorList[i]
                behaviorList[i] = behavior.copy(
                    extension = behavior.extension.copyI(
                        value = behaviorLogic.getValue(
                            extension = behavior.extension,
                            index = i,
                            changeVariable = false
                        ) ?: 0
                    )
                )
            }

            /**
             * update custom temp
             */
            filterWithPreviousIndex(
                list = tempItemList,
                predicate = { it.type == ItemType.SensorType.I_S_CUSTOM_TEMP }
            ) { index, customTemp ->
                tempItemList[index] = tempItemList[index].copy(
                    extension = (tempItemList[index].extension as CustomTemp).copy(
                        value = TempLogic.getValue(customTemp.id) ?: 0
                    )
                )

            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}