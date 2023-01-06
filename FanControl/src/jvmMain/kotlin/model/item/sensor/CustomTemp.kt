package model.item.sensor

enum class CustomTempType {
    average,
    max,
    min;

    companion object {
        infix fun from(value: String): CustomTempType = CustomTempType.values().first { it.name == value }
    }
}

data class CustomTemp(
    val customType: CustomTempType = CustomTempType.average,
    val sensorIdList: MutableList<Long> = mutableListOf()
) : SensorExtension