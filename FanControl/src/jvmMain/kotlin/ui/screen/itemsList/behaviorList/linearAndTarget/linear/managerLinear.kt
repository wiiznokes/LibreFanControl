package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import State
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Linear
import ui.component.managerAddItemListChoice
import ui.component.managerExpandItem
import ui.component.managerListChoice
import ui.component.managerText
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.screen.itemsList.behaviorList.linearAndTarget.linAndTarSuffixes
import ui.screen.itemsList.behaviorList.linearAndTarget.managerNumberChoice
import ui.screen.itemsList.behaviorList.linearAndTarget.managerNumberTextField
import ui.utils.Resources

private val viewModel: LinearVM = LinearVM()


@Composable
fun linearBody(
    behavior: Behavior,
    index: Int
) {
    baseItemBody(
        iconPainter = Resources.getIcon("items/linear40"),
        iconContentDescription = Resources.getString("ct/linear"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = behavior
    ) {

        val linear = behavior.extension as Linear

        val customTempList = viewModel.tempItemList.filter { it.type == ItemType.SensorType.I_S_CUSTOM_TEMP }

        managerListChoice(
            text = with(linear.tempSensorId) {
                when {
                    this == null -> null
                    this > 0 -> viewModel.tempList.first {
                        it.id == this
                    }.libName

                    this < 0 -> customTempList.first {
                        it.id == this
                    }.name

                    else -> throw IllegalArgumentException()
                }
            },
            onItemClick = {
                viewModel.setTemp(
                    index = index,
                    tempSensorId = it
                )
            },
            ids = viewModel.tempList.map { it.id } + customTempList.map { it.id },
            names = viewModel.tempList.map { it.libName } + customTempList.map { it.name }
        )

        val expanded = remember(
            behavior.id,
            State.settings.collectAsState().value.configId
        ) {
            mutableStateOf(false)
        }

        managerExpandItem(
            value = linear.value,
            color = MaterialTheme.colorScheme.onSurface,
            expanded = expanded
        )

        if (expanded.value) {
            val linearValues = linearValues(linear)

            for (i in 0..3) {

                val text: MutableState<String> = remember(
                    behavior.id,
                    State.settings.collectAsState().value.configId
                ) {
                    mutableStateOf(linearValues[i].toString())
                }

                managerNumberChoice(
                    text = {
                        managerNumberTextField(
                            text = text,
                            opposedValue = when (i) {
                                0 -> linearValues[1]
                                1 -> linearValues[0]
                                2 -> linearValues[3]
                                3 -> linearValues[2]
                                else -> throw Exception("impossible index")
                            },
                            onValueChange = {
                                viewModel.onChange(
                                    index = index,
                                    value = it,
                                    type = linearTypes[i]
                                )
                            },
                            type = linearTypes[i]
                        )
                    },
                    prefix = linearPrefixes[i],
                    suffix = linAndTarSuffixes[i],
                    increase = {
                        text.value = viewModel.increase(
                            index = index,
                            type = linearTypes[i]
                        )
                    },
                    decrease = {
                        text.value = viewModel.decrease(
                            index = index,
                            type = linearTypes[i]
                        )
                    },
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Composable
fun linearAddItem() {
    baseItemAddItem(
        iconPainter = Resources.getIcon("items/linear40"),
        iconContentDescription = Resources.getString("ct/linear"),
        name = Resources.getString("add_item/linear_name"),
        onEditClick = { viewModel.addBehavior(viewModel.defaultLinear) },
        type = ItemType.BehaviorType.I_B_LINEAR
    ) {
        managerAddItemListChoice(
            name = Resources.getString("add_item/temp_name")
        )

        managerExpandItem(
            value = 50,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            enabled = false
        )

        for (i in 0..3) {
            managerNumberChoice(
                text = {
                    managerText(
                        text = linearValues[i],
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                prefix = linearPrefixes[i],
                suffix = linAndTarSuffixes[i],
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}