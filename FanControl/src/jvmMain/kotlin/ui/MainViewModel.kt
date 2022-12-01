package ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import hardware.external.ExternalManager
import hardware.external.getOS
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import model.BaseItem
import model.behavior.Flat
import model.hardware.control.FanControl
import model.hardware.sensor.FanSpeed
import model.hardware.sensor.Temp
import ui.event.Event
import ui.event.Source
import javax.print.attribute.standard.Destination

class MainViewModel {
    private val externalManager = ExternalManager(getOS())
    private var jobUpdate: Job? = null
    private val _uiState: MutableStateFlow<UiStates>
    val uiState: StateFlow<UiStates>

    init {
        externalManager.start()
        _uiState = MutableStateFlow(
            UiStates(
                fanList = externalManager.getFan(),
                tempList = externalManager.getTemp(),
                controlList = externalManager.getControl(),
                behaviorList = mutableStateListOf(),

                addFanList = mutableStateListOf(),
                addTempList = mutableStateListOf(),
                addControlList = mutableStateListOf(),
                addBehaviorList = mutableStateListOf(),
            )
        )
        uiState = _uiState.asStateFlow()
        startUpdate()
    }



    fun onEvent(event: Event) {
        when (event) {
            is Event.Initialisation.FetchHardware -> {
                _uiState.update {
                    it.copy(
                        fanList = externalManager.getFan(),
                        tempList = externalManager.getTemp(),
                        controlList = externalManager.getControl()
                    )
                }
            }

            is Event.Initialisation.Stop -> {
                runBlocking {
                    updateShouldStop = true
                    jobUpdate?.cancel()
                    jobUpdate = null
                    externalManager.stop()
                }
            }

            is Event.Organisation.Add -> {
                when(event.type.itemTypeSpecific) {
                    ITEM_TYPE_SPECIFIC.FLAT -> {
                    changeBehaviorList(event.id, _uiState.value.addBehaviorList, _uiState.value.behaviorList)
                    }
                    ITEM_TYPE_SPECIFIC.FAN_CONTROL -> {
                        changeControlList(event.id, _uiState.value.addControlList, _uiState.value.controlList)
                    }
                    ITEM_TYPE_SPECIFIC.FAN_SPEED -> {
                        changeFanList(event.id, _uiState.value.addFanList, _uiState.value.fanList)
                    }
                    ITEM_TYPE_SPECIFIC.TEMP -> {
                        changeTempList(event.id, _uiState.value.addTempList, _uiState.value.tempList)
                    }
                }
            }

            is Event.Organisation.Remove -> {
                when(event.type.itemTypeSpecific) {
                    ITEM_TYPE_SPECIFIC.FLAT -> {
                        changeBehaviorList(event.id, _uiState.value.behaviorList, _uiState.value.addBehaviorList)
                    }
                    ITEM_TYPE_SPECIFIC.FAN_CONTROL -> {
                        changeControlList(event.id, _uiState.value.controlList, _uiState.value.addControlList)
                    }
                    ITEM_TYPE_SPECIFIC.FAN_SPEED -> {
                        changeFanList(event.id, _uiState.value.fanList, _uiState.value.addFanList)
                    }
                    ITEM_TYPE_SPECIFIC.TEMP -> {
                        changeTempList(event.id, _uiState.value.tempList, _uiState.value.addTempList)
                    }
                }
            }

            is Event.Configuration.Save -> {
            }
            is Event.Configuration.Remove -> {
            }
            is Event.Configuration.Apply -> {
            }

            is Event.Item.SetName -> {
                when(event.source) {
                    Source.HOME_VIEW -> {
                        when(event.type.itemTypeSpecific) {
                            ITEM_TYPE_SPECIFIC.FLAT -> _uiState.value.behaviorList[event.index] = _uiState.value.behaviorList[event.index].copy(
                                name = event.name
                            )
                            ITEM_TYPE_SPECIFIC.FAN_CONTROL -> _uiState.value.controlList[event.index] = _uiState.value.controlList[event.index].copy(
                                name = event.name
                            )
                            ITEM_TYPE_SPECIFIC.FAN_SPEED -> _uiState.value.fanList[event.index] = _uiState.value.fanList[event.index].copy(
                                name = event.name
                            )

                            ITEM_TYPE_SPECIFIC.TEMP -> _uiState.value.tempList[event.index] = _uiState.value.tempList[event.index].copy(
                                name = event.name
                            )
                        }
                    }
                    Source.ADD_VIEW ->
                        when(event.type.itemTypeSpecific) {
                            ITEM_TYPE_SPECIFIC.FLAT -> _uiState.value.addBehaviorList[event.index] = _uiState.value.addBehaviorList[event.index].copy(
                                name = event.name
                            )
                            ITEM_TYPE_SPECIFIC.FAN_CONTROL -> _uiState.value.addControlList[event.index] = _uiState.value.addControlList[event.index].copy(
                                name = event.name
                            )
                            ITEM_TYPE_SPECIFIC.FAN_SPEED -> _uiState.value.addFanList[event.index] = _uiState.value.addFanList[event.index].copy(
                                name = event.name
                            )

                            ITEM_TYPE_SPECIFIC.TEMP -> _uiState.value.addTempList[event.index] = _uiState.value.addTempList[event.index].copy(
                                name = event.name
                            )
                    }
                }

            }

            is Event.Item.Control.SetValue -> {
                externalManager.setControl(event.id, event.isAuto, event.value)
            }

            is Event.Item.Control.SetBehaviorId -> {
                _uiState.value.addControlList[event.index] = _uiState.value.addControlList[event.index].copy(
                    behaviorId = event.behaviorId
                )
            }
        }
    }

    private var updateShouldStop = false


    private fun changeFanList(id: String, sourceList: SnapshotStateList<FanSpeed>, destinationList: SnapshotStateList<FanSpeed>) {
        destinationList.add(
            sourceList.removeAt(
                sourceList.indexOfFirst {
                    it.id == id
                }
            )
        )
    }

    private fun changeControlList(id: String, sourceList: SnapshotStateList<FanControl>, destinationList: SnapshotStateList<FanControl>) {
        destinationList.add(
            sourceList.removeAt(
                sourceList.indexOfFirst {
                    it.id == id
                }
            )
        )
    }

    private fun changeTempList(id: String, sourceList: SnapshotStateList<Temp>, destinationList: SnapshotStateList<Temp>) {
        destinationList.add(
            sourceList.removeAt(
                sourceList.indexOfFirst {
                    it.id == id
                }
            )
        )
    }

    private fun changeBehaviorList(id: String, sourceList: SnapshotStateList<Flat>, destinationList: SnapshotStateList<Flat>) {
        destinationList.add(
            sourceList.removeAt(
                sourceList.indexOfFirst {
                    it.id == id
                }
            )
        )
    }


    private fun startUpdate() {
        jobUpdate = CoroutineScope(Dispatchers.Default).launch {
            while (!updateShouldStop) {
                externalManager.updateFan(_uiState.value.fanList)
                externalManager.updateTemp(_uiState.value.tempList)
                externalManager.updateControl(_uiState.value.controlList)

                delay(2000L)
            }
        }
    }

}