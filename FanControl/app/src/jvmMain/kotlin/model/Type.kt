package model


class UnspecifiedTypeException : Exception()

interface ItemType {
    enum class ControlType : ItemType {
        I_C_FAN;

        companion object {
            infix fun from(value: String): ControlType = ControlType.values().first { it.name == value }
        }
    }

    enum class BehaviorType : ItemType {
        I_B_FLAT,
        I_B_LINEAR,
        I_B_TARGET;

        companion object {
            infix fun from(value: String): BehaviorType = BehaviorType.values().first { it.name == value }
        }
    }

    enum class SensorType : ItemType {
        I_S_FAN,
        I_S_TEMP,
        I_S_CUSTOM_TEMP;

        companion object {
            infix fun from(value: String): SensorType = SensorType.values().first { it.name == value }
        }
    }
}

interface HardwareType {
    enum class SensorType : HardwareType {
        H_S_FAN,
        H_S_TEMP;

        companion object {
            infix fun from(value: String): SensorType = SensorType.values().first { it.name == value }
        }
    }

    enum class ControlType : HardwareType {
        H_C_FAN;

        companion object {
            infix fun from(value: String): ControlType = ControlType.values().first { it.name == value }
        }
    }
}
