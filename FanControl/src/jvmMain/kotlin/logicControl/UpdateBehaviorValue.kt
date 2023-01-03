package logicControl

import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.hardware.Sensor
import model.item.behavior.BehaviorItem
import model.item.behavior.LinearBehavior


/**
 * class used to update behavior value on the screen
 * Should be used only if app is visible and in a coroutine
 */
class UpdateBehaviorValue (
    private val behaviorItemList: SnapshotStateList<BehaviorItem> = State.behaviorItemList,
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {
    fun update() {
        behaviorItemList.forEachIndexed { index, behavior ->
            when(behavior.type) {
                ItemType.BehaviorType.I_B_LINEAR ->  {
                    val value = valueLinear(behavior.extension as LinearBehavior, tempList)
                    behaviorItemList[index] = behaviorItemList[index].copy(
                        extension = (behaviorItemList[index].extension as LinearBehavior).copy(
                            value = value ?: 0
                        )
                    )
                }
                else -> {}
            }
        }
    }
}