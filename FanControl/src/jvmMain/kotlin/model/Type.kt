package model


class UnspecifiedTypeException : Exception()

interface ItemType {
    enum class ControlType : ItemType {
        I_C_FAN,
        I_C_UNSPECIFIED
    }

    enum class BehaviorType : ItemType {
        I_B_FLAT,
        I_B_LINEAR,
        I_B_TARGET,
        I_B_UNSPECIFIED
    }

    enum class SensorType : ItemType {
        I_S_FAN,
        I_S_TEMP,
        I_S_CUSTOM_TEMP,
        I_S_UNSPECIFIED
    }
}

fun getItemType(str: String): ItemType {
    return when (str) {
        ItemType.ControlType.I_C_FAN.toString() -> ItemType.ControlType.I_C_FAN
        ItemType.ControlType.I_C_UNSPECIFIED.toString() -> ItemType.ControlType.I_C_UNSPECIFIED

        ItemType.BehaviorType.I_B_FLAT.toString() -> ItemType.BehaviorType.I_B_FLAT
        ItemType.BehaviorType.I_B_LINEAR.toString() -> ItemType.BehaviorType.I_B_LINEAR
        ItemType.BehaviorType.I_B_TARGET.toString() -> ItemType.BehaviorType.I_B_TARGET
        ItemType.BehaviorType.I_B_UNSPECIFIED.toString() -> ItemType.BehaviorType.I_B_UNSPECIFIED

        ItemType.SensorType.I_S_FAN.toString() -> ItemType.SensorType.I_S_FAN
        ItemType.SensorType.I_S_TEMP.toString() -> ItemType.SensorType.I_S_TEMP
        ItemType.SensorType.I_S_UNSPECIFIED.toString() -> ItemType.SensorType.I_S_UNSPECIFIED
        else -> throw UnspecifiedTypeException()
    }
}


interface HardwareType {
    enum class SensorType : HardwareType {
        H_S_FAN,
        H_S_TEMP,
        H_S_UNSPECIFIED
    }
}
