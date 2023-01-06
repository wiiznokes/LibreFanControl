package ui.screen.itemsList.behaviorList.linearAndTarget

import ui.screen.itemsList.behaviorList.linearAndTarget.linear.LinearParams
import ui.screen.itemsList.behaviorList.linearAndTarget.target.TargetParams
import ui.utils.Resources


val linAndTarSuffixes = listOf(
    Resources.getString("unity/degree"),
    Resources.getString("unity/degree"),
    Resources.getString("unity/percent"),
    Resources.getString("unity/percent")
)

interface LinAndTarParams

fun isError(params: LinAndTarParams, str: String, opposedValue: Int): Boolean {
    val value = try {
        str.toInt()
    } catch (e: NumberFormatException) {
        return true
    }

    when (params) {
        is LinearParams -> {
            return try {
                when (params) {
                    LinearParams.MIN_TEMP -> value >= opposedValue
                    LinearParams.MAX_TEMP -> value <= opposedValue
                    LinearParams.MIN_FAN_SPEED -> value >= opposedValue
                    LinearParams.MAX_FAN_SPEED -> value <= opposedValue
                }
            } catch (e: NumberFormatException) {
                true
            }
        }

        is TargetParams -> {
            return try {
                when (params) {
                    TargetParams.IDLE_TEMP -> value >= opposedValue
                    TargetParams.LOAD_TEMP -> value <= opposedValue
                    TargetParams.IDLE_FAN_SPEED -> value >= opposedValue
                    TargetParams.LOAD_FAN_SPEED -> value <= opposedValue
                }
            } catch (e: NumberFormatException) {
                true
            }
        }

        else -> throw IllegalArgumentException()
    }
}

