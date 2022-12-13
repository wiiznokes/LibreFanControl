package model


interface ItemType {
    enum class ControlType : ItemType {
        FAN
    }

    enum class BehaviorType : ItemType {
        FLAT,
        LINEAR,
        TARGET
    }

    enum class SensorType : ItemType {
        FAN,
        TEMP
    }
}
