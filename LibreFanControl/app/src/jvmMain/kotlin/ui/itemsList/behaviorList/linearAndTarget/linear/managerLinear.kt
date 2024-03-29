package ui.screen.itemsList.behaviorList.linearAndTarget.linear

import FState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.item.ICustomTemp
import model.item.ILinear
import model.item.TempHelper.Companion.getNameIorH
import ui.component.managerExpandItem
import ui.component.managerListChoice
import ui.component.managerNumberTextField
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.screen.itemsList.behaviorList.linearAndTarget.linAndTarSuffixes
import ui.screen.itemsList.behaviorList.linearAndTarget.managerNumberChoice
import ui.theme.LocalSpaces
import utils.Resources

private val viewModel: LinearVM = LinearVM()


@Composable
fun linearBody(
    linear: ILinear,
    index: Int,
) {
    baseItemBody(
        icon = Resources.getIcon("items/linear24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = linear,
        modifier = Modifier
            .width(220.dp)
    ) {

        val customTempList = viewModel.iTemps.filterIsInstance<ICustomTemp>()


        managerListChoice(
            text = getNameIorH(
                hTempId = linear.hTempId.value,
                customTemps = customTempList
            ),
            onItemClick = {
                viewModel.setTemp(
                    index = index,
                    hTempId = it
                )
            },
            ids = viewModel.hTemps.map { it.id } + customTempList.map { it.id },
            names = viewModel.hTemps.map { it.name } + customTempList.map { it.name.value }
        )

        val expanded = remember(
            linear.id,
            FState.settings.confId.value
        ) {
            mutableStateOf(false)
        }

        Spacer(Modifier.height(LocalSpaces.current.medium))

        managerExpandItem(
            value = linear.value.value,
            expanded = expanded
        ) {
            Spacer(Modifier.height(LocalSpaces.current.small))

            val linearValues = linearValues(linear)

            for (i in 0..3) {

                val text: MutableState<String> = remember(
                    linear.id,
                    FState.settings.confId.value
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
                    }
                )
            }
        }
    }
}


@Composable
fun linearAddItem() {
    baseItemAddItem(
        icon = Resources.getIcon("items/linear24"),
        name = Resources.getString("add_item/name/linear"),
        onEditClick = { viewModel.addBehavior(viewModel.defaultLinear()) },
        text = Resources.getString("add_item/info/linear")
    )
}