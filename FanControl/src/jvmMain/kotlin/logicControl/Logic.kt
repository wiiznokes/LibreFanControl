package logicControl

import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import model.ItemType
import model.hardware.Control
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import model.item.behavior.LinearBehavior
import ui.utils.Resources

class Logic(
    private val _controlList: MutableStateFlow<SnapshotStateList<Control>> = State._controlList,
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,


    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {

    fun update(
        // libIndex, isAuto, value
        setControl: (Int, Boolean, Int) -> Unit
    ) {
        _controlItemList.value.filter {
            it.visible && it.isActive && it.behaviorName != Resources.getString("none")
        }.forEach { control ->
            val behavior = _behaviorItemList.value.find { behavior ->
                behavior.name == control.behaviorName
            }
            val value = when (behavior!!.type) {
                ItemType.BehaviorType.FLAT -> {
                    behavior.flatBehavior!!.value
                }

                ItemType.BehaviorType.LINEAR -> {
                    val f = getAffine(
                        minTemp = behavior.linearBehavior!!.minTemp,
                        maxTemp = behavior.linearBehavior.minTemp,
                        minSpeed = behavior.linearBehavior.minTemp,
                        maxSpeed = behavior.linearBehavior.minTemp,
                    )
                    getSpeed(
                        f = f,
                        linearBehavior = behavior.linearBehavior,
                        tempValue = _tempList.value.find {
                            it.libId == behavior.linearBehavior.sensorId
                        }!!.value
                    )
                }

                ItemType.BehaviorType.TARGET -> TODO()
            }

            setControl(
                _controlList.value.find {
                    it.libId == control.sensorId
                }!!.libIndex,
                true,
                value
            )
        }
    }



    private fun getSpeed(f: Pair<Int, Int>, linearBehavior: LinearBehavior, tempValue: Int): Int {
        return when {
            tempValue <= linearBehavior.minTemp -> linearBehavior.minFanSpeed
            tempValue >= linearBehavior.maxTemp -> linearBehavior.maxFanSpeed
            else -> {
                f.first * tempValue + f.second
            }
        }
    }


    private fun getAffine(minTemp: Int, maxTemp: Int, minSpeed: Int, maxSpeed: Int): Pair<Int, Int> {
        /*
            y = ax + b
            x -> temp
            y -> speed
        */
        val a = (maxSpeed - minSpeed) / (maxTemp - minTemp)
        val b = minSpeed - a * minTemp
        return Pair(a, b)
    }
}