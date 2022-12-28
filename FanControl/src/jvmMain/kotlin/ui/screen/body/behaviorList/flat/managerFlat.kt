package ui.screen.body.behaviorList.flat


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.item.behavior.BehaviorItem
import model.item.behavior.FlatBehavior
import ui.component.baseItemBody
import ui.component.managerText
import ui.utils.Resources


private val viewModel: FlatBehaviorViewModel = FlatBehaviorViewModel()

@Composable
fun flatBehavior(
    behavior: BehaviorItem,
    index: Int,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    editModeActivated: Boolean,
) {

    val flatBehavior = behavior.extension as FlatBehavior

    baseItemBody(
        iconPainter = Resources.getIcon("horizontal_rule"),
        iconContentDescription = Resources.getString("ct/flat"),
        onNameChange = onNameChange,
        editModeActivated = editModeActivated,
        onEditClick = onEditClick,
        item = behavior
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            managerText(Resources.getString("fan_speed"))

            Box {
                Row {


                    IconButton(
                        onClick = {
                            viewModel.onLess(
                                index = index,
                                value = flatBehavior.value
                            )
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("remove"),
                            contentDescription = Resources.getString("ct/decrease")
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.onMore(
                                index = index,
                                value = flatBehavior.value
                            )
                        }
                    ) {
                        Icon(
                            painter = Resources.getIcon("add"),
                            contentDescription = Resources.getString("ct/increase")
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Slider(
                value = flatBehavior.value.toFloat(),
                steps = 100,
                valueRange = 0f..100f,
                onValueChange = {
                    viewModel.onChange(index, it.toInt())
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            )

            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            managerText(
                text = "${flatBehavior.value} ${Resources.getString("unity/percent")}"
            )
        }
    }
}



