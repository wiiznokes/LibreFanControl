package ui

import androidx.compose.runtime.mutableStateListOf
import hardware.external.ExternalManager
import hardware.external.getOS
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ui.event.Event

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
                when (event.globalItemType) {
                    FlagGlobalItemType.FAN_CONTROL -> {
                        _uiState.value.controlList.add(_uiState.value.addControlList.removeAt(event.index))
                    }

                    FlagGlobalItemType.FAN_SENSOR -> {
                        _uiState.value.fanList.add(_uiState.value.addFanList.removeAt(event.index))
                    }

                    FlagGlobalItemType.TEMP_SENSOR -> {
                        _uiState.value.tempList.add(_uiState.value.addTempList.removeAt(event.index))
                    }

                    FlagGlobalItemType.FLAT_BEHAVIOR -> {
                        _uiState.value.behaviorList.add(_uiState.value.addBehaviorList.removeAt(event.index))
                    }
                }
            }

            is Event.Organisation.Remove -> {
                when (event.globalItemType) {
                    FlagGlobalItemType.FAN_CONTROL -> {
                        _uiState.value.addControlList.add(_uiState.value.controlList.removeAt(event.index))
                    }

                    FlagGlobalItemType.FAN_SENSOR -> {
                        _uiState.value.addFanList.add(_uiState.value.fanList.removeAt(event.index))
                    }

                    FlagGlobalItemType.TEMP_SENSOR -> {
                        _uiState.value.addTempList.add(_uiState.value.tempList.removeAt(event.index))
                    }

                    FlagGlobalItemType.FLAT_BEHAVIOR -> {
                        _uiState.value.addBehaviorList.add(_uiState.value.behaviorList.removeAt(event.index))
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
                when (event.globalItemType) {
                    FlagGlobalItemType.FAN_CONTROL -> {
                        _uiState.value.controlList[event.index] = _uiState.value.controlList[event.index].copy(
                            name = event.name
                        )
                    }

                    FlagGlobalItemType.FAN_SENSOR -> {
                        _uiState.value.fanList[event.index] = _uiState.value.fanList[event.index].copy(
                            name = event.name
                        )
                    }

                    FlagGlobalItemType.TEMP_SENSOR -> {
                        _uiState.value.tempList[event.index] = _uiState.value.tempList[event.index].copy(
                            name = event.name
                        )
                    }

                    FlagGlobalItemType.FLAT_BEHAVIOR -> {
                        _uiState.value.behaviorList[event.index] = _uiState.value.behaviorList[event.index].copy(
                            name = event.name
                        )
                    }
                }
            }

            is Event.Item.Control.SetValue -> {
                //externalManager.setControl(event.index, event.isAuto, event.value)
            }

            is Event.Item.Control.SetBehaviorId -> {
                _uiState.value.addControlList[event.index] = _uiState.value.addControlList[event.index].copy(
                    behaviorId = event.behaviorId
                )
            }
        }
    }

    private var updateShouldStop = false


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