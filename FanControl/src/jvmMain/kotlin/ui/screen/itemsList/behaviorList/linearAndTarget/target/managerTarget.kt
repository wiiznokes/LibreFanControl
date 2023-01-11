package ui.screen.itemsList.behaviorList.linearAndTarget.target

import State
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import model.ItemType
import model.item.behavior.Behavior
import model.item.behavior.Target
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

private val viewModel: TargetVM = TargetVM()


@Composable
fun targetBody(
    behavior: Behavior,
    index: Int
) {
    baseItemBody(
        iconPainter = Resources.getIcon("items/my_location40"),
        iconContentDescription = Resources.getString("ct/target"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = behavior
    ) {

        val target = behavior.extension as Target

        val customTempList = viewModel.tempItemList.filter { it.type == ItemType.SensorType.I_S_CUSTOM_TEMP }
        managerListChoice(
            text = with(target.tempSensorId) {
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
            value = target.value,
            color = MaterialTheme.colorScheme.onSurface,
            expanded = expanded
        )

        if (expanded.value) {
            val targetValues = targetValues(target)

            for (i in 0..3) {

                val text: MutableState<String> = remember(
                    behavior.id,
                    State.settings.collectAsState().value.configId
                ) {
                    mutableStateOf(targetValues[i].toString())
                }

                managerNumberChoice(
                    text = {
                        managerNumberTextField(
                            text = text,
                            opposedValue = when (i) {
                                0 -> targetValues[1]
                                1 -> targetValues[0]
                                2 -> targetValues[3]
                                3 -> targetValues[2]
                                else -> throw Exception("impossible index")
                            },
                            onValueChange = {
                                viewModel.onChange(
                                    index = index,
                                    value = it,
                                    type = targetTypes[i]
                                )
                            },
                            type = targetTypes[i]
                        )
                    },
                    prefix = targetPrefixes[i],
                    suffix = linAndTarSuffixes[i],
                    increase = {
                        text.value = viewModel.increase(
                            index = index,
                            type = targetTypes[i]
                        )
                    },
                    decrease = {
                        text.value = viewModel.decrease(
                            index = index,
                            type = targetTypes[i]
                        )
                    },
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Composable
fun targetAddItem() {
    baseItemAddItem(
        iconPainter = Resources.getIcon("items/my_location40"),
        iconContentDescription = Resources.getString("ct/target"),
        name = Resources.getString("add_item/target_name"),
        onEditClick = { viewModel.addBehavior(viewModel.defaultTarget) },
        type = ItemType.BehaviorType.I_B_TARGET
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
                        text = targetValues[i],
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                prefix = targetPrefixes[i],
                suffix = linAndTarSuffixes[i],
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}