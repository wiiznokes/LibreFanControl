package ui.screen.topBar.configuration

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ConfigurationModel
import ui.component.managerConfigNameRoundedTextField
import ui.component.managerListChoice
import ui.utils.Resources
import utils.checkNameTaken

val viewModel = ConfigurationViewModel()

@Composable
fun managerConfig() {
    if (viewModel.configList.value.isNotEmpty()) {
        val id = viewModel.idConfig.value
        if (id != null) {
            managerConfigWithId(
                id = id,
                configList = viewModel.configList.value
            )
        } else {
            managerConfigListChoice(
                textContent = {
                    managerConfigNameRoundedTextField(
                        text = it,
                        modifier = Modifier
                            .width(200.dp)
                            .height(35.dp),
                    )
                },
                configList = viewModel.configList.value
            )
        }
    }

    managerAddConfig()
}


@Composable
private fun managerConfigWithId(
    id: Long,
    configList: SnapshotStateList<ConfigurationModel>
) {
    val index = viewModel.configList.value.indexOfFirst {
        it.id == viewModel.idConfig.value
    }

    val text: MutableState<String> = remember(
        id
    ) {
        mutableStateOf(configList[index].name)
    }

    IconButton(
        onClick = {
            viewModel.saveConfiguration(text.value, index, id)
        }
    ) {
        Icon(
            painter = Resources.getIcon("save_as"),
            contentDescription = Resources.getString("ct/save_conf")
        )
    }

    Spacer(Modifier.width(5.dp))

    managerConfigListChoice(
        text = text.value,
        textContent = {
            managerConfigNameRoundedTextField(
                modifier = Modifier
                    .widthIn(200.dp, 250.dp)
                    .height(35.dp),
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
                placeholder = Resources.getString("label/conf_name")
            )
        },
        configList = viewModel.configList.value
    )
}

@Composable
private fun managerConfigListChoice(
    text: String? = null,
    textContent: @Composable (String) -> Unit,
    configList: SnapshotStateList<ConfigurationModel>
) {
    managerListChoice(
        text = text,
        textContent = textContent,
        baseModifier = Modifier,
        dropDownModifier = Modifier
            .width(180.dp),
        onItemClick = {
            viewModel.onChangeConfiguration(it)
        },
        iconContent = { id, index ->
            IconButton(
                onClick = { viewModel.removeConfiguration(id, index) }
            ) {
                Icon(
                    painter = Resources.getIcon("delete_forever"),
                    contentDescription = Resources.getString("ct/remove_conf")
                )
            }
        },
        ids = configList.map { it.id },
        names = configList.map { it.name }
    )
}