package model


interface ItemType {
    enum class ControlType : ItemType {
        FAN,
        UNSPECIFIED
    }

    enum class BehaviorType : ItemType {
        FLAT,
        LINEAR,
        TARGET,
        UNSPECIFIED
    }

    enum class SensorType : ItemType {
        FAN,
        TEMP,
        UNSPECIFIED
    }
}
