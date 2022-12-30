package logicControl

import Application
import State
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import model.ItemType
import model.hardware.Sensor
import model.item.ControlItem
import model.item.SensorItem
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import model.item.behavior.LinearBehavior

class Logic(
    private val _fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    private val _tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,


    private val _controlItemList: MutableStateFlow<SnapshotStateList<ControlItem>> = State._controlItemList,
    private val _behaviorItemList: MutableStateFlow<SnapshotStateList<BehaviorItem>> = State._behaviorItemList,
    private val _fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    private val _tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {

    val controlItemList = _controlItemList.asStateFlow()
    val behaviorItemList = _behaviorItemList.asStateFlow()

    fun update() {
        controlItemList.value.filter {
            it.visible && !it.isAuto && it.behaviorId != null
        }.forEach label@{ control ->
            val behavior = behaviorItemList.value.find { behavior ->
                behavior.itemId == control.behaviorId
            }
            val value = when (behavior!!.type) {
                ItemType.BehaviorType.I_B_FLAT -> {
                    (behavior.extension as FlatBehavior).value
                }

                ItemType.BehaviorType.I_B_LINEAR -> {
                    val linearBehavior = behavior.extension as LinearBehavior
                    if (linearBehavior.tempSensorId == null) {
                        // continue keyword of forEach loop
                        // we only return from the lambda expression
                        // (control here)
                        return@label
                    }

                    val f = getAffine(linearBehavior)

                    getSpeed(
                        f = f,
                        linearBehavior = linearBehavior,
                        tempValue = _tempList.value.find {
                            it.id == linearBehavior.tempSensorId
                        }!!.value
                    )
                }

                ItemType.BehaviorType.I_B_TARGET -> TODO()

                else -> throw Exception("unspecified item type")
            }
            Application.setControl(
                libIndex = control.libIndex,
                isAuto = false,
                value = value
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


    private fun getAffine(linearBehavior: LinearBehavior): Pair<Int, Int> {
        /*
            y = ax + b
            x -> temp
            y -> speed
        */

        val xa = linearBehavior.minTemp
        val xb = linearBehavior.maxTemp
        val ya = linearBehavior.minFanSpeed
        val yb = linearBehavior.maxFanSpeed

        val a = (yb - ya) / (xb - xa)
        val b = ya - a * xa
        return Pair(a, b)
    }
}