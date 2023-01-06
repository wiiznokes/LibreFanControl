package logicControl.behavior

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.hardware.Sensor
import model.item.behavior.Linear
import kotlin.math.roundToInt


class LinearLogic(
    private val tempList: SnapshotStateList<Sensor> = State.sensorLists.tempList,
) {
    fun getValue(linear: Linear): Int? {
        return getSpeed(
            f = getAffine(linear),
            linear = linear,
            tempValue = getTempValue(linear.tempSensorId, tempList) ?: return null
        )
    }

    private fun getSpeed(f: Affine, linear: Linear, tempValue: Int): Int {
        return when {
            tempValue <= linear.minTemp -> linear.minFanSpeed
            tempValue >= linear.maxTemp -> linear.maxFanSpeed
            else -> (f.a * tempValue + f.b).roundToInt()
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
