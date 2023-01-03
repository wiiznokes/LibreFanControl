package ui.screen.topBar.configuration

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ConfigurationModel
import ui.component.managerListChoice
import ui.utils.Resources
import utils.checkNameTaken

val viewModel = ConfigurationVM()

@Composable
fun configuration() {
    if (viewModel.settings.value.configList.isNotEmpty()) {
        val id = viewModel.settings.collectAsState().value.configId
        if (id != null) {
            configurationWithId(
                id = id,
                configList = viewModel.settings.value.configList
            )
        } else {
            configurationListChoice(
                textContent = {
                    managerConfigNameRoundedTextField(
                        value = it,
                        enabled = false
                    )
                },
                configList = viewModel.settings.value.configList,
                enabled = !viewModel.controlChange.collectAsState().value
            )
        }
    }
    addConfiguration()
}


@Composable
private fun configurationWithId(
    id: Long,
    configList: SnapshotStateList<ConfigurationModel>
) {
    val index = viewModel.settings.value.configList.indexOfFirst {
        it.id == id
    }

    val text: MutableState<String> = remember(id) {
        mutableStateOf(configList[index].name)
    }

    IconButton(
        onClick = { viewModel.saveConfiguration(text.value, index, id) }
    ) {
        Icon(
            painter = Resources.getIcon("save_as"),
            contentDescription = Resources.getString("ct/save_conf"),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }

    Spacer(Modifier.width(5.dp))

    configurationListChoice(
        text = text.value,
        enabled = !viewModel.controlChange.collectAsState().value,
        textContent = {
            managerConfigNameRoundedTextField(
                value = text.value,
                onValueChange = {
                    checkNameTaken(
                        names = configList.map { config ->
                            config.name
                        },
                        name = it,
                        index = index
                    )
                },
                id = id,
                text = text,
                enabled = true
            )
        },
        configList = viewModel.settings.value.configList
    )
}

@Composable
private fun configurationListChoice(
    text: String? = null,
    enabled: Boolean = true,
    textContent: @Composable (String) -> Unit,
    configList: SnapshotStateList<ConfigurationModel>
) {
    managerListChoice(
        text = text,
        textContent = textContent,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        enabled = enabled,
        baseModifier = Modifier,
        itemModifier = Modifier.width(180.dp),
        onItemClick = { viewModel.onChangeConfiguration(it) },
        iconContent = { id, index ->
            IconButton(
                onClick = { viewModel.removeConfiguration(id, index) }
            ) {
                Icon(
                    painter = Resources.getIcon("delete_forever"),
                    contentDescription = Resources.getString("ct/remove_conf"),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        ids = configList.map { it.id },
        names = configList.map { it.name }
    )
}