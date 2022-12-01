package ui

import hardware.external.ExternalManager
import hardware.external.getOS
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel {
    private val externalManager = ExternalManager(getOS())
    private var jobUpdate: Job? = null
    private val _uiState: MutableStateFlow<UiStates>
    val uiState: StateFlow<UiStates>

    init {
        externalManager.start()
        _uiState = MutableStateFlow(
            UiStates(
                fans = externalManager.getFan(),
                temps = externalManager.getTemp(),
                controls = externalManager.getControl()
            )
        )
        uiState = _uiState.asStateFlow()
        startUpdate()
    }


    fun onEvent(event: Event) {
        when (event) {
            is Event.SetControl -> {
                externalManager.setControl(event.id, event.isAuto, event.value)
            }

            is Event.Reload -> {
                _uiState.update {
                    it.copy(
                        fans = externalManager.getFan(),
                        temps = externalManager.getTemp(),
                        controls = externalManager.getControl()
                    )
                }
            }

            is Event.Stop -> {
                runBlocking {
                    updateShouldStop = true
                    jobUpdate?.cancel()
                    jobUpdate = null
                    externalManager.stop()
                }
            }
        }
    }

    private var updateShouldStop = false

    private fun startUpdate() {
        jobUpdate = CoroutineScope(Dispatchers.Default).launch {
            while (!updateShouldStop) {
                externalManager.updateFan(_uiState.value.fans)
                externalManager.updateTemp(_uiState.value.temps)
                externalManager.updateControl(_uiState.value.controls)

                delay(2000L)
            }
        }
    }

}