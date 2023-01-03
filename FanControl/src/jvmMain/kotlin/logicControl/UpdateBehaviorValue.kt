package logicControl

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.ItemType
import model.hardware.Sensor
import model.item.behavior.Behavior
import model.item.behavior.Linear


/**
 * class used to update behavior value on the screen
 * Should be used only if app is visible and in a coroutine
 */
class UpdateBehaviorValue(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList,
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList
) {

    fun update() {

        try {
            for (i in behaviorList.indices) {
                val behavior = behaviorList[i]
                when (behavior.type) {
                    ItemType.BehaviorType.I_B_LINEAR -> {
                        val value = valueLinear(behavior.extension as Linear, tempList)
                        behaviorList[i] = behavior.copy(
                            extension = behavior.extension.copy(
                                value = value ?: 0
                            )
                        )
                    }

                    else -> {}
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}