package ui.screen.topBar

import FState
import UiState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ConfInfo
import model.item.BaseI.Companion.checkNameTaken
import ui.component.ListChoiceDefault
import ui.component.managerListChoice
import ui.component.managerNameTextField
import ui.screen.dialogs.confDialogs.ConfVM
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources

val viewModel = ConfVM()

@Composable
fun confTopBar() {
    if (viewModel.settings.confInfoList.isNotEmpty()) {

        val settings = viewModel.settings
        val index = FState.settings.getIndexInfo()

        remember {

        }
        val text = remember(settings.confId.value) {
            when (index) {
                null -> mutableStateOf(Resources.getString("common/none"))
                else -> mutableStateOf(settings.confInfoList[index].name.value)
            }
        }


        if (settings.confId.value != null) {
            IconButton(
                onClick = { viewModel.saveConfiguration(text.value) }
            ) {
                Icon(
                    painter = Resources.getIcon("topBar/save40"),
                    contentDescription = null,
                    tint = LocalColors.current.onMainTopBar
                )
            }
        }


        configurationListChoice(
            text = text,
            confInfoList = settings.confInfoList,
            currentId = settings.confId.value,
            currentIndex = index,
        )

    }


    /*
        used to know if add conf button should trigger
        because when the dialog appears, this button is
        still focused, and will trigger if Enter is pressed
    */
    val keyEnterPressed = remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            if (keyEnterPressed.value)
                keyEnterPressed.value = false
            else
                FState.ui.dialogExpanded.value = UiState.Dialog.NEW_CONF
        }
    ) {
        Icon(
            painter = Resources.getIcon("sign/plus/add40"),
            contentDescription = null,
            tint = LocalColors.current.onMainTopBar
        )
    }
}


@Composable
private fun configurationListChoice(
    text: MutableState<String>,
    currentId: String?,
    currentIndex: Int?,
    confInfoList: SnapshotStateList<ConfInfo>,
) {

    val minWidth = 150.dp
    managerListChoice(
        text = text.value,
        textContent = {
            managerNameTextField(
                ids = Pair(null, currentId),
                text = text,
                onValueChange = { value ->
                    checkNameTaken(
                        names = confInfoList.map { config ->
                            config.name.value
                        },
                        name = value,
                        index = currentIndex
                    )
                },
                modifier = Modifier
                    .widthIn(minWidth, 200.dp)
                    .height(35.dp),
                placeholder = Resources.getString("label/conf_name"),
                color = LocalColors.current.input,
                onColor = LocalColors.current.onInput,
                shape = MaterialTheme.shapes.large,
                enabled = currentId != null
            )
        },
        colors = ListChoiceDefault.listChoiceColors(
            base = LocalColors.current.mainTopBar,
            onBase = LocalColors.current.onMainTopBar,
            container = LocalColors.current.inputVariant,
            onContainer = LocalColors.current.onInputVariant
        ),
        baseModifier = Modifier,
        itemModifier = Modifier.size(width = minWidth, height = 35.dp),
        onItemClick = { viewModel.onChangeConfiguration(it) },
        size = 40,
        iconContent = { id, index ->
            Icon(
                modifier = Modifier
                    .clickable { viewModel.removeConfiguration(id, index) }
                    .padding(end = LocalSpaces.current.small),
                painter = Resources.getIcon("select/delete_forever24"),
                contentDescription = null,
                tint = LocalColors.current.onInputVariant
            )
        },
        ids = confInfoList.map { it.id },
        names = confInfoList.map { it.name.value },
        contentNameClickable = false
    )
}