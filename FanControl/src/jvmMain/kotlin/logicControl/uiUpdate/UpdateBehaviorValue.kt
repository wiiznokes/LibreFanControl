package logicControl.uiUpdate

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import logicControl.behaviorLogic.BehaviorLogic
import model.item.behavior.Behavior


/**
 * class used to update behavior value on the screen
 * Should be used only if app is visible and in a coroutine
 */
class UpdateBehaviorValue(
    private val behaviorList: SnapshotStateList<Behavior> = State.behaviorList
) {

    private val behaviorLogic = BehaviorLogic()

    fun update() {

        try {
            for (i in behaviorList.indices) {
                val behavior = behaviorList[i]

                behaviorList[i] = behavior.copy(
                    extension = behavior.extension.copy_(
                        value = behaviorLogic.getValue(
                            extension = behavior.extension,
                            index = i,
                            changeVariable = false
                        ) ?: 0
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}