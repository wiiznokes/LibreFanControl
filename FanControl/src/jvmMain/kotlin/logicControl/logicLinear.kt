package logicControl

import model.hardware.Sensor
import model.item.behavior.LinearBehavior

fun valueLinear(linearBehavior: LinearBehavior, tempList: List<Sensor>): Int? {
    if (linearBehavior.tempSensorId == null)
        return null

    return getSpeed(
        f = getAffine(linearBehavior),
        linearBehavior = linearBehavior,
        tempValue = tempList.find {
            it.id == linearBehavior.tempSensorId
        }!!.value
    )
}

private fun getSpeed(f: Pair<Int, Int>, linearBehavior: LinearBehavior, tempValue: Int): Int {
    return when {
        tempValue <= linearBehavior.minTemp -> linearBehavior.minFanSpeed
        tempValue >= linearBehavior.maxTemp -> linearBehavior.maxFanSpeed
        else -> {
            f.first * tempValue + f.second
        }
    }
}


private fun getAffine(linearBehavior: LinearBehavior): Pair<Int, Int> {
    /*
        y = ax + b
        x -> temp
        y -> speed
    */

    val xa = linearBehavior.minTemp
    val xb = linearBehavior.maxTemp
    val ya = linearBehavior.minFanSpeed
    val yb = linearBehavior.maxFanSpeed

    val a = (yb - ya) / (xb - xa)
    val b = ya - a * xa
    return Pair(a, b)
}