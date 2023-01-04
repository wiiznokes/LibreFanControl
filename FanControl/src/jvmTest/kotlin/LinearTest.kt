import logicControl.valueLinear
import model.HardwareType
import model.hardware.Sensor
import model.item.behavior.Linear
import org.junit.Test

class LinearTest {

    private val baseTemp = Sensor(
        libIndex = 0,
        libId = "",
        libName = "",
        type = HardwareType.SensorType.H_S_TEMP,
        id = 0,
        value = 40
    )
    private val baseTempList = listOf(baseTemp)

    private val baseLinear = Linear(
        tempSensorId = 0,
        minTemp = 20,
        maxTemp = 80,
        minFanSpeed = 40,
        maxFanSpeed = 60
    )

    @Test
    fun `valueLinear returns null when Linear has no temp sensor ID`() {
        assert(valueLinear(baseLinear.copy(tempSensorId = null), baseTempList) == null)
    }

    @Test
    fun `valueLinear returns min fan speed when temp is below min temp`() {
        val tempList = listOf(baseTemp.copy(value = 10))
        assert(40 == valueLinear(baseLinear, tempList))
    }

    @Test
    fun `valueLinear returns max fan speed when temp is above max temp`() {
        val tempList = listOf(baseTemp.copy(value = 90))
        assert(60 == valueLinear(baseLinear, tempList))
    }

    @Test
    fun `valueLinear returns correct fan speed when temp is within min and max temp range`() {
        val a = valueLinear(baseLinear, baseTempList)
        assert(47 == a)
    }
}