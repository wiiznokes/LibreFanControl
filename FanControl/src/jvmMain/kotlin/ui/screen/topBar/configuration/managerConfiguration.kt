package ui.screen.topBar.configuration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import model.ConfigurationModel
import ui.component.*
import ui.utils.Resources
import utils.checkNameTaken

val viewModel = ConfigurationViewModel()

@Composable
fun managerModifyConfig() {
    val dialogExpanded = remember { mutableStateOf(false) }

    if (viewModel.configList.value.isNotEmpty()) {
        if (viewModel.idConfig.value != null) {
            managerModifyConfig(
                index = viewModel.configList.value.indexOfFirst {
                    it.id == viewModel.idConfig.value
                },
                configList = viewModel.configList.value
            )
        } else {
            managerModifyConfig(
                configList = viewModel.configList.value
            )
        }
    }

    // add configuration

    /*
        used to know if add conf button should trigger
        because when the dialog appears, this button is
        still focused, and will trigger if Enter is pressed
    */
    val keyEnterPressed = remember {
        mutableStateOf(false)
    }
    IconButton(
        onClick = {
            if (keyEnterPressed.value)
                keyEnterPressed.value = false
            else
                dialogExpanded.value = true
        }
    ) {
        Icon(
            painter = Resources.getIcon("add"),
            contentDescription = Resources.getString("ct/add_conf")
        )
    }

    if (dialogExpanded.value) {
        managerDialogAddConfiguration(
            enabled = dialogExpanded,
            keyEnterPressed = keyEnterPressed
        )
    }
}


@Composable
private fun managerModifyConfig(
    index: Int,
    configList: SnapshotStateList<ConfigurationModel>
) {
    val expanded = remember { mutableStateOf(false) }

    val text: MutableState<String> = remember(
        configList[index].id
    ) {
        mutableStateOf(configList[index].name)
    }


    IconButton(
        onClick = {
            viewModel.saveConfiguration(text.value, index, configList[index].id)
        }
    ) {
        Icon(
            painter = Resources.getIcon("save_as"),
            contentDescription = Resources.getString("ct/save_conf")
        )
    }

    Spacer(Modifier.width(5.dp))

    managerListChoice(
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
                id = configList[index].id,
                text = text,
                placeholder = Resources.getString("label/conf_name")
            )
        },
        expanded = expanded
    ) {
        dropdownMenuItemContent(
            expanded = expanded,
            configList = configList
        )
    }
}


@Composable
private fun managerModifyConfig(
    configList: SnapshotStateList<ConfigurationModel>
) {
    val text: MutableState<String> = remember(
        configList[index].id
    ) {
        mutableStateOf(configList[index].name)
    }

    managerlistChoice(
        text = text,
        
    )

    val expanded = remember { mutableStateOf(false) }

    managerBaseDropdownMenu(
        text = Resources.getString("none"),
        textContent = {
            managerConfigNameRoundedTextField(
                text = it,
                modifier = Modifier
                    .width(200.dp)
                    .height(35.dp),
            )
        },
        expanded = expanded
    ) {
        managerDropDownContent(
            expanded = expanded,
            text = Resources.getString("none"),
            onItemClick = {
                viewModel.onChangeConfiguration(null)
            }
        )
        configList.forEachIndexed { index, config ->
            managerDropDownContent(
                expanded = expanded,
                text = config,
                onItemClick = {
                    viewModel.onChangeConfiguration(config.id)
                }
            ) {
                IconButton(
                    onClick = { viewModel.removeConfiguration(config.id, index) }
                ) {
                    Icon(
                        painter = Resources.getIcon("delete_forever"),
                        contentDescription = Resources.getString("ct/remove_conf")
                    )
                }
            }
        }
    }
}