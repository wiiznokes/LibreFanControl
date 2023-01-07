package logicControl.behavior

import model.UnspecifiedTypeException
import model.item.behavior.BehaviorExtension
import model.item.behavior.Flat
import model.item.behavior.Linear
import model.item.behavior.Target

class BehaviorLogic {

    private var linearLogic = LinearLogic()
    private val targetLogic = TargetLogic()

    fun getValue(
        extension: BehaviorExtension,
        index: Int,
        changeVariable: Boolean
    ): Int? {
        return when (extension) {
            is Flat -> extension.value
            is Linear -> linearLogic.getValue(extension)
            is Target -> {
                if (changeVariable)
                    targetLogic.getValueAndChangeVariable(extension, index)
                else
                    targetLogic.getValue(extension)
            }

            else -> throw UnspecifiedTypeException()
        }
    }

}