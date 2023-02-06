package ui.screen.itemsList.sensor.body.tempList

import State
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.hardware.HTemp
import model.item.CustomTempType
import model.item.ICustomTemp
import ui.component.PainterType
import ui.component.managerExpandItem
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemBody
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources


@Composable
fun baseCustomTempBody(
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    iCustomTemp: ICustomTemp,
    onCustomTypeChange: (CustomTempType) -> Unit,
    onAddTempSensor: (String) -> Unit,
    hTemps: List<HTemp>,
    onRemoveTemp: (String) -> Unit,
) {

    baseItemBody(
        icon = Resources.getIcon("items/thermostat24"),
        onNameChange = onNameChange,
        onEditClick = onEditClick,
        item = iCustomTemp
    ) {
        managerListChoice(
            text = Resources.getString("custom_temp/${iCustomTemp.customTempType.value}"),
            onItemClick = { onCustomTypeChange(it!!) },
            ids = CustomTempType.values().toList(),
            names = CustomTempType.values().map {
                Resources.getString("custom_temp/$it")
            },
            addNoneItem = false
        )


        val expanded = remember(
            iCustomTemp.id,
            State.settings.configId.value
        ) {
            mutableStateOf(false)
        }

        Spacer(Modifier.height(LocalSpaces.current.medium))

        managerExpandItem(
            value = iCustomTemp.value.value,
            color = LocalColors.current.onMainContainer,
            expanded = expanded,
            suffix = Resources.getString("unity/degree")
        ) {

            Spacer(Modifier.height(LocalSpaces.current.small))

            val filterListSensor = hTemps.filter {
                !iCustomTemp.hTempIds.contains(it.id)
            }

            if (filterListSensor.isNotEmpty()) {
                managerListChoice(
                    text = Resources.getString("custom_temp/add_temp"),
                    onItemClick = { onAddTempSensor(it!!) },
                    ids = filterListSensor.map { it.id },
                    names = filterListSensor.map { it.name },
                    painterType = PainterType.ADD,
                    addNoneItem = false
                )
            }

            iCustomTemp.hTempIds.forEach { id ->
                selectedSensor(
                    name = hTemps.first { it.id == id }.name,
                    id = id,
                    onRemove = onRemoveTemp
                )
            }
        }
    }
}


@Composable
private fun selectedSensor(
    name: String,
    id: String,
    onRemove: (String) -> Unit,
) {
    Spacer(Modifier.height(LocalSpaces.current.medium))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        managerText(
            text = name,
            color = LocalColors.current.onMainContainer,
            style = MaterialTheme.typography.bodySmall
        )

        Icon(
            modifier = Modifier
                .clickable { onRemove(id) },
            painter = Resources.getIcon("select/close/close20"),
            contentDescription = null,
            tint = LocalColors.current.onMainContainer
        )
    }
}