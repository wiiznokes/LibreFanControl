package logicControl.behavior

import logicControl.temp.TempLogic
import model.item.behavior.Linear
import kotlin.math.roundToInt


class LinearLogic {
    fun getValue(linear: Linear): Int? {
        val tempValue = TempLogic.getValue(linear.tempSensorId) ?: return null

        return getSpeed(
            linear = linear,
            tempValue = tempValue
        )
    }

    private fun getSpeed(linear: Linear, tempValue: Int): Int {
        return when {
            tempValue <= linear.minTemp -> linear.minFanSpeed
            tempValue >= linear.maxTemp -> linear.maxFanSpeed
            else -> getAffine(linear).let { (it.a * tempValue + it.b).roundToInt() }
        }
    }


    private fun getAffine(linear: Linear): Affine {
        /**
         * y = ax + b
         * x -> temp
         * y -> speed
         */

        val xa = linear.minTemp
        val xb = linear.maxTemp
        val ya = linear.minFanSpeed
        val yb = linear.maxFanSpeed


        val a: Float = (yb - ya).toFloat() / (xb - xa).toFloat()
        return Affine(
            a = a,
            b = ya - a * xa
        )
    }

    data class Affine(
        val a: Float,
        val b: Float
    )
}
