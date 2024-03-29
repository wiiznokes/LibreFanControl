package model.item

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import model.hardware.HTemp
import model.item.TempHelper.Companion.getValueIorH
import kotlin.math.roundToInt


interface BaseIBehavior : BaseI {
    val value: MutableState<Int>
}


class IFlat(
    name: String,
    override val id: String,
    value: Int = 50,
) : BaseIBehavior {
    override val name: MutableState<String> = mutableStateOf(name)

    override val value: MutableState<Int> = mutableStateOf(value)

}


class ILinear(
    name: String,
    override val id: String,
    value: Int = 0,


    minTemp: Int = 45,
    maxTemp: Int = 65,
    minFanSpeed: Int = 15,
    maxFanSpeed: Int = 100,

    tempId: String? = null,
) : BaseIBehavior {
    override val name: MutableState<String> = mutableStateOf(name)

    override val value: MutableState<Int> = mutableStateOf(value)

    val minTemp: MutableState<Int> = mutableStateOf(minTemp)
    val maxTemp: MutableState<Int> = mutableStateOf(maxTemp)
    val minFanSpeed: MutableState<Int> = mutableStateOf(minFanSpeed)
    val maxFanSpeed: MutableState<Int> = mutableStateOf(maxFanSpeed)

    val hTempId: MutableState<String?> = mutableStateOf(tempId)


    private fun isValid(): Boolean = hTempId.value != null


    fun calcAndSet(hTemps: List<HTemp>, iCustomTemps: List<ICustomTemp>) {
        if (!isValid()) {
            value.value = 0
            return
        }

        val tempValue = getValueIorH(
            hTempId = hTempId.value,
            customTemps = iCustomTemps,
            hTemps = hTemps
        )

        if (tempValue == null) {
            value.value = 0
            return
        }

        value.value = getSpeed(
            f = getAffine(),
            tempValue = tempValue
        )
    }

    private fun getSpeed(f: Affine, tempValue: Int): Int {
        return when {
            tempValue <= minTemp.value -> minFanSpeed.value
            tempValue >= maxTemp.value -> maxFanSpeed.value
            else -> (f.a * tempValue + f.b).roundToInt()
        }
    }


    private fun getAffine(): Affine {
        /**
         * y = ax + b
         * x -> temp
         * y -> speed
         */

        val xa = minTemp.value
        val xb = maxTemp.value
        val ya = minFanSpeed.value
        val yb = maxFanSpeed.value


        val a: Float = (yb - ya).toFloat() / (xb - xa).toFloat()
        return Affine(
            a = a,
            b = ya - a * xa
        )
    }

    data class Affine(
        val a: Float,
        val b: Float,
    )
}


class ITarget(
    name: String,
    override val id: String,
    value: Int = 0,

    idleTemp: Int = 45,
    loadTemp: Int = 60,
    idleFanSpeed: Int = 25,
    loadFanSpeed: Int = 100,

    tempId: String? = null,

    ) : BaseIBehavior {
    override val name: MutableState<String> = mutableStateOf(name)

    override val value: MutableState<Int> = mutableStateOf(value)

    val idleTemp: MutableState<Int> = mutableStateOf(idleTemp)
    val loadTemp: MutableState<Int> = mutableStateOf(loadTemp)
    val idleFanSpeed: MutableState<Int> = mutableStateOf(idleFanSpeed)
    val loadFanSpeed: MutableState<Int> = mutableStateOf(loadFanSpeed)

    val hTempId: MutableState<String?> = mutableStateOf(tempId)


    private var idleHasBeenReach = false


    private fun isValid(): Boolean =
        hTempId.value != null


    fun calcAndSet(hTemps: List<HTemp>, iCustomTemps: List<ICustomTemp>) {
        if (!isValid()) {
            idleHasBeenReach = false
            value.value = 0
            return
        }

        val tempValue = getValueIorH(
            hTempId = hTempId.value,
            customTemps = iCustomTemps,
            hTemps = hTemps
        )

        if (tempValue == null) {
            idleHasBeenReach = false
            value.value = 0
            return
        }

        value.value = when (idleHasBeenReach) {
            true -> {
                if (tempValue < loadTemp.value) {
                    idleFanSpeed.value
                } else {
                    idleHasBeenReach = false
                    loadFanSpeed.value
                }
            }

            false -> {
                if (tempValue <= idleTemp.value) {
                    idleHasBeenReach = true
                    idleFanSpeed.value
                } else {
                    loadFanSpeed.value
                }
            }
        }
    }
}