package ui.screen.itemsList.behaviorList.linear

import State
import androidx.compose.runtime.*
import model.ItemType
import model.item.behavior.BehaviorItem
import model.item.behavior.LinearBehavior
import ui.component.managerAddItemListChoice
import ui.component.managerListChoice
import ui.component.managerNumberTextField
import ui.component.managerText
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.utils.Resources

private val viewModel: LinearVM = LinearVM()


@Composable
fun linearBody(
    behavior: BehaviorItem,
    index: Int,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit
) {
    baseItemBody(
        iconPainter = Resources.getIcon("linear"),
        iconContentDescription = Resources.getString("ct/linear"),
        onNameChange = onNameChange,
        onEditClick = onEditClick,
        item = behavior
    ) {

        val linearBehavior = behavior.extension as LinearBehavior

        managerListChoice(
            text = if (linearBehavior.tempSensorId != null) {
                viewModel.tempList.find {
                    it.id == linearBehavior.tempSensorId
                }!!.libName
            } else null,
            onItemClick = {
                viewModel.setTemp(
                    index = index,
                    tempSensorId = it
                )
            },
            ids = viewModel.tempList.map { it.id },
            names = viewModel.tempList.map { it.libName }
        )

        val linearValues = linearValues(linearBehavior)
        val linearPrefixes = linearPrefixes
        val linearSuffixes = linearSuffixes
        val linearTypes = linearTypes


        for (i in 0..3) {

            val text: MutableState<String> = remember(
                behavior.itemId,
                State.settings.collectAsState().value.configId
            ) {
                mutableStateOf(linearValues[i].toString())
            }

            baseLinear(
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
                suffix = linearSuffixes[i],
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
                }
            )
        }
    }
}


@Composable
fun linearAddItem(
    onEditClick: () -> Unit,
) {
    baseItemAddItem(
        iconPainter = Resources.getIcon("linear"),
        iconContentDescription = Resources.getString("ct/linear"),
        name = Resources.getString("add_item/linear_name"),
        onEditClick = onEditClick,
        type = ItemType.BehaviorType.I_B_LINEAR
    ) {
        managerAddItemListChoice(
            name = Resources.getString("add_item/temp_name")
        )

        val linearValues = linearValues
        val linearPrefixes = linearPrefixes
        val linearSuffixes = linearSuffixes

        for (i in 0..3) {
            baseLinear(
                text = { managerText(linearValues[i]) },
                prefix = linearPrefixes[i],
                suffix = linearSuffixes[i],
            )
        }
    }
}