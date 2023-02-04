import logicControl.behavior.LinearLogic
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
        id = 1,
        value = 40
    )

    private val baseLinear = Linear(
        tempSensorId = 1,
        minTemp = 20,
        maxTemp = 80,
        minFanSpeed = 40,
        maxFanSpeed = 60
    )

    private val linearLogic = LinearLogic()

    @Test
    fun `valueLinear returns null when Linear has no temp sensor ID`() {
        assert(linearLogic.getValue(baseLinear.copy(tempSensorId = null)) == null)
    }

    @Test
    fun `valueLinear returns min fan speed when temp is below min temp`() {
        addAndRemove(baseTemp.copy(value = 10))
        assert(40 == linearLogic.getValue(baseLinear))
    }

    @Test
    fun `valueLinear returns max fan speed when temp is above max temp`() {
        addAndRemove(baseTemp.copy(value = 90))
        assert(60 == linearLogic.getValue(baseLinear))
    }

    @Test
    fun `valueLinear returns correct fan speed when temp is within min and max temp range`() {
        addAndRemove(baseTemp)
        val a = linearLogic.getValue(baseLinear)
        assert(47 == a)
    }


    private val tempList = State.hSensorsList.hTemps
    private fun addAndRemove(temp: Sensor) {
        if (tempList.size > 0) {
            tempList.removeAt(0)
        }
        tempList.add(0, temp)
    }
}