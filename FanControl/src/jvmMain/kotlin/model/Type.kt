package model


class UnspecifiedTypeException : Exception()

interface ItemType {
    enum class ControlType : ItemType {
        C_FAN,
        C_UNSPECIFIED
    }

    enum class BehaviorType : ItemType {
        B_FLAT,
        B_LINEAR,
        B_TARGET,
        B_UNSPECIFIED
    }

    enum class SensorType : ItemType {
        S_FAN,
        S_TEMP,
        S_UNSPECIFIED
    }
}

fun getType(str: String): ItemType {
    return when (str) {
        ItemType.ControlType.C_FAN.toString() -> ItemType.ControlType.C_FAN
        ItemType.ControlType.C_UNSPECIFIED.toString() -> ItemType.ControlType.C_UNSPECIFIED

        ItemType.BehaviorType.B_FLAT.toString() -> ItemType.BehaviorType.B_FLAT
        ItemType.BehaviorType.B_LINEAR.toString() -> ItemType.BehaviorType.B_LINEAR
        ItemType.BehaviorType.B_TARGET.toString() -> ItemType.BehaviorType.B_TARGET
        ItemType.BehaviorType.B_UNSPECIFIED.toString() -> ItemType.BehaviorType.B_UNSPECIFIED

        ItemType.SensorType.S_FAN.toString() -> ItemType.SensorType.S_FAN
        ItemType.SensorType.S_TEMP.toString() -> ItemType.SensorType.S_TEMP
        ItemType.SensorType.S_UNSPECIFIED.toString() -> ItemType.SensorType.S_UNSPECIFIED
        else -> throw UnspecifiedTypeException()
    }
}
