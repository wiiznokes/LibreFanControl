package logicControl

import model.hardware.Sensor
import model.item.behavior.Linear

fun valueLinear(linear: Linear, tempList: List<Sensor>): Int? {
    if (linear.tempSensorId == null)
        return null

    return getSpeed(
        f = getAffine(linear),
        linear = linear,
        tempValue = tempList.find {
            it.id == linear.tempSensorId
        }!!.value
    )
}

private fun getSpeed(f: Pair<Int, Int>, linear: Linear, tempValue: Int): Int {
    return when {
        tempValue <= linear.minTemp -> linear.minFanSpeed
        tempValue >= linear.maxTemp -> linear.maxFanSpeed
        else -> {
            f.first * tempValue + f.second
        }
    }
}


private fun getAffine(linear: Linear): Pair<Int, Int> {
    /*
        y = ax + b
        x -> temp
        y -> speed
    */

    val xa = linear.minTemp
    val xb = linear.maxTemp
    val ya = linear.minFanSpeed
    val yb = linear.maxFanSpeed

    val a = (yb - ya) / (xb - xa)
    val b = ya - a * xa
    return Pair(a, b)
}