package ui.screen.itemsList.behaviorList.linearAndTarget.target

import State
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.item.BaseI
import model.item.ICustomTemp
import model.item.ITarget
import ui.component.managerExpandItem
import ui.component.managerListChoice
import ui.component.managerNumberTextField
import ui.screen.itemsList.baseItemAddItem
import ui.screen.itemsList.baseItemBody
import ui.screen.itemsList.behaviorList.linearAndTarget.linAndTarSuffixes
import ui.screen.itemsList.behaviorList.linearAndTarget.managerNumberChoice
import ui.theme.LocalColors
import ui.theme.LocalSpaces
import ui.utils.Resources

private val viewModel: TargetVM = TargetVM()


@Composable
fun targetBody(
    target: ITarget,
    index: Int,
) {
    baseItemBody(
        icon = Resources.getIcon("items/my_location24"),
        onNameChange = { viewModel.setName(it, index) },
        onEditClick = { viewModel.remove(index) },
        item = target,
        modifier = Modifier
            .width(220.dp)
    ) {

        val customTempList = viewModel.iTemps.filterIsInstance<ICustomTemp>()

        println(target.hTempId.value)
        managerListChoice(
            text = with(target.hTempId.value) {
                when (BaseI.getPrefix(this)) {
                    null -> null

                    BaseI.ICustomTempPrefix -> customTempList.first {
                        it.id == this
                    }.name.value

                    else -> viewModel.hTemps.first {
                        it.id == this
                    }.name
                }
            },
            onItemClick = {
                viewModel.setTemp(
                    index = index,
                    hTempId = it
                )
            },
            ids = viewModel.hTemps.map { it.id } + customTempList.map { it.id },
            names = viewModel.hTemps.map { it.name } + customTempList.map { it.name.value }
        )

        Spacer(Modifier.height(LocalSpaces.current.medium))

        val expanded = remember(
            target.id,
            State.settings.confId.value
        ) {
            mutableStateOf(false)
        }

        managerExpandItem(
            value = target.value.value,
            color = LocalColors.current.onMainContainer,
            expanded = expanded
        ) {
            Spacer(Modifier.height(LocalSpaces.current.small))

            val targetValues = targetValues(target)

            for (i in 0..3) {

                val text: MutableState<String> = remember(
                    target.id,
                    State.settings.confId.value
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
                    color = LocalColors.current.onMainContainer
                )
            }
        }
    }
}


@Composable
fun targetAddItem() {
    baseItemAddItem(
        icon = Resources.getIcon("items/my_location24"),
        name = Resources.getString("add_item/name/target"),
        onEditClick = { viewModel.addBehavior(viewModel.defaultTarget()) },
        text = Resources.getString("add_item/info/target")
    )
}