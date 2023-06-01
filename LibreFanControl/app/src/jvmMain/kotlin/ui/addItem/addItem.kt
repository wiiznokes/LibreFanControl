package ui.addItem

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.managerText
import ui.screen.itemsList.behaviorList.behaviorAddItemList
import ui.screen.itemsList.controlList.controlAddItemList
import ui.screen.itemsList.sensor.addItem.sensorAddItemList
import ui.theme.LocalColors
import utils.Resources


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun addItem(choiceState: MutableState<ChoiceState>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.secondBackground)
    ) {
        addItemChoice(choiceState)

        Divider(
            modifier = Modifier.padding(bottom = 10.dp),
            color = LocalColors.current.onSecondBackground
        )



        AnimatedContent(
            targetState = choiceState.value,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = {
                        it * targetState.animationSign
                    }
                ) with slideOutHorizontally(
                    targetOffsetX = {
                        it * targetState.animationSign * -1
                    }
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (it.current) {
                    ChoiceType.CONTROL -> controlAddItemList()
                    ChoiceType.BEHAVIOR -> behaviorAddItemList()
                    ChoiceType.SENSOR -> sensorAddItemList()
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun addItemChoice(state: MutableState<ChoiceState>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalColors.current.secondHeader),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                state.value = updateChoiceState(
                    state.value.copy(
                        animationSign = -1
                    )
                )
            }
        ) {
            Icon(
                painter = Resources.getIcon("arrow/notch/line_start_arrow_notch40"),
                contentDescription = Resources.getString("ct/previous"),
                tint = LocalColors.current.onSecondHeader
            )
        }

        AnimatedContent(
            targetState = state.value,
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = {
                        it * targetState.animationSign
                    }
                ) + fadeIn() with slideOutHorizontally(
                    targetOffsetX = {
                        it * targetState.animationSign * -1
                    }
                ) + fadeOut()
            }
        ) {
            managerText(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = it.title,
                color = LocalColors.current.onSecondHeader,
                style = MaterialTheme.typography.titleSmall
            )
        }

        IconButton(
            onClick = {
                state.value = updateChoiceState(
                    state.value.copy(
                        animationSign = 1
                    )
                )
            }
        ) {
            Icon(
                painter = Resources.getIcon("arrow/notch/line_end_arrow_notch40"),
                contentDescription = Resources.getString("ct/next"),
                tint = LocalColors.current.onSecondHeader
            )
        }
    }
}