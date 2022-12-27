package model


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
