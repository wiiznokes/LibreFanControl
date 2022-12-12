package ui.screen.addItem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.component.managerText


@Composable
fun addItem(
    modifier: Modifier
) {
    val currentChoiceType: MutableState<ChoiceType> = remember {
        mutableStateOf(ChoiceType.BEHAVIOR)
    }
    val choiceStates = ChoiceStates()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        addItemChoice(currentChoiceType, choiceStates)

    }



}



@Composable
fun addItemChoice(currentChoiceType: MutableState<ChoiceType>, choiceStates: ChoiceStates) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                currentChoiceType.value = choiceStates.getState(currentChoiceType.value)
                    .previous
            }
        ) {
            Text(
                text = "pre"
            )
        }

        managerText(
            modifier = Modifier
                .background(Color.Black),
            text = choiceStates.getState(currentChoiceType.value)
                .title
        )

        Button(
            onClick = {
                currentChoiceType.value = choiceStates.getState(currentChoiceType.value)
                    .next
            }
        ) {
            Text(
                text = "next"
            )
        }
    }
}