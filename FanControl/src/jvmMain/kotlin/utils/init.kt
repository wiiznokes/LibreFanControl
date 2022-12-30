package utils

import State
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.ConfigurationModel
import model.ItemType
import model.hardware.Sensor
import model.item.SensorItem
import settings.SETTINGS_FILE_NAME
import settings.Settings
import settings.Settings.Companion.getConfigList
import java.io.File

// init sensor list, used when there is no config at start
fun initSensor(
    fanList: MutableStateFlow<SnapshotStateList<Sensor>> = State._fanList,
    tempList: MutableStateFlow<SnapshotStateList<Sensor>> = State._tempList,

    fanItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._fanItemList,
    tempItemList: MutableStateFlow<SnapshotStateList<SensorItem>> = State._tempItemList
) {
    fanList.value.forEach { fanSensor ->
        fanItemList.update {
            it.add(
                SensorItem(
                    name = fanSensor.libName,
                    type = ItemType.SensorType.I_S_FAN,
                    itemId = getAvailableId(
                        ids = it.map { item -> item.itemId }
                    ),
                    sensorId = fanSensor.id
                )
            )
            it
        }
    }

    tempList.value.forEach { tempSensor ->
        tempItemList.update {
            it.add(
                SensorItem(
                    name = tempSensor.libName,
                    type = ItemType.SensorType.I_S_FAN,
                    itemId = getAvailableId(
                        ids = it.map { item -> item.itemId }
                    ),
                    sensorId = tempSensor.id
                )
            )
            it
        }
    }
}


/*
    initialize the settings.json file using the settings.sot.json file
    which serves as a source of truth, to avoid committing a modified settings.json file.

    This avoids using "smudge", the git tool, because it is really not practical.
*/

private const val SETTINGS_SOT_FILE_NAME = "settings.sot.json"
fun initSettings() {
    val localSettingFile = File(DIR_CONF + SETTINGS_FILE_NAME)

    if (!localSettingFile.exists()) {
        val settingsSotFile = File(DIR_CONF + SETTINGS_SOT_FILE_NAME)
        localSettingFile.writeText(settingsSotFile.readText())
    }
}


// initialize config, returns configId if it exists, otherwise null
fun initConfig(
    configList: MutableStateFlow<SnapshotStateList<ConfigurationModel>>,
    idConfig: MutableStateFlow<MutableState<Long?>>
): Long? {

    val configId = checkConfig()
    idConfig.update {
        it.value = configId
        it
    }

    getConfigList(configList)

    return configId
}


// returns configId if it exists, otherwise null
private fun checkConfig(): Long? {
    return Settings.getSetting("config")
}